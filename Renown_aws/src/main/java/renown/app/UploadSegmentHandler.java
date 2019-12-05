package renown.app;

import java.io.ByteArrayInputStream;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import renown.app.http.*;
import renown.app.model.Segment;
import renown.app.db.SegmentsDAO;

public class UploadSegmentHandler implements RequestHandler<UploadSegmentRequest,UploadSegmentResponse> {

	LambdaLogger logger;
	
	// To access S3 storage
	private AmazonS3 s3 = null;
		
	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "renownsegments/";
	public static final String TEST_BUCKET = "testsegments/";
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	public boolean uploadSegment(String seg_id, String character, String line, String url) throws Exception { 
		if (logger != null) { logger.log("in uploadSegment"); }
		SegmentsDAO dao = new SegmentsDAO();
		
		// check if present
		Segment exist = dao.getSegment(seg_id);
		Segment segment = new Segment(seg_id, character, line, url);
		if (exist == null) {
			return dao.addSegment(segment);
		} else {
			return false;
		}
	}
	
	/** Create S3 bucket
	 * 
	 * @throws Exception 
	 */
	public boolean createSystemSegment(String seg_id, byte[] contents) throws Exception {
		if (logger != null) { logger.log("in createSystemSegment"); }
		
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
			logger.log("attach to S3 succeed");
		}

		String bucket = REAL_BUCKET;
		boolean useTestDB = System.getenv("TESTING") != null;
		if (useTestDB) {
			bucket = TEST_BUCKET;
		}

		ByteArrayInputStream bais = new ByteArrayInputStream(contents);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(contents.length);
		
		// makes the object publicly visible
		PutObjectResult res = s3.putObject(new PutObjectRequest(bucket, seg_id, bais, omd)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		
		// if we ever get here, then whole thing was stored
		return true;
	}
	
	@Override 
	public UploadSegmentResponse handleRequest(UploadSegmentRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		UploadSegmentResponse response;
		try {
			byte[] encoded = java.util.Base64.getDecoder().decode(req.base64EncodedValue);
			if (req.system) {
				if (createSystemSegment(req.seg_id, encoded)) {
					response = new UploadSegmentResponse(req.seg_id);
				} else {
					response = new UploadSegmentResponse(req.seg_id, 400);
				}
			} else {
				String contents = new String(encoded);
				String value = String.valueOf(contents);
				
				if (uploadSegment(req.seg_id, req.character, req.line, value)) {
					response = new UploadSegmentResponse(req.seg_id);
				} else {
					response = new UploadSegmentResponse(req.seg_id, 400);
				}
			}
		} catch (Exception e) {
			response = new UploadSegmentResponse("Unable to create constant: " + req.seg_id + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
}

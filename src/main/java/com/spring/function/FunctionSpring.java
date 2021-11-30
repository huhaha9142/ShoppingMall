package com.spring.function;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.S3ResponseMetadata;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.analytics.AnalyticsConfiguration;
import com.amazonaws.services.s3.model.inventory.InventoryConfiguration;
import com.amazonaws.services.s3.model.metrics.MetricsConfiguration;
import com.amazonaws.services.s3.waiters.AmazonS3Waiters;
import com.spring.dto.ProductVO;
import com.spring.dto.S3Componet;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class FunctionSpring {
	
	private final AmazonS3 amazonS3= new AmazonS3() {
		
		@Override
		public AmazonS3Waiters waiters() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public UploadPartResult uploadPart(UploadPartRequest request) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public PresignedUrlUploadResult upload(PresignedUrlUploadRequest presignedUrlUploadRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void shutdown() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setS3ClientOptions(S3ClientOptions clientOptions) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setRegion(com.amazonaws.regions.Region region) throws IllegalArgumentException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public SetPublicAccessBlockResult setPublicAccessBlock(SetPublicAccessBlockRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public SetObjectTaggingResult setObjectTagging(SetObjectTaggingRequest setObjectTaggingRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public SetObjectRetentionResult setObjectRetention(SetObjectRetentionRequest setObjectRetentionRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void setObjectRedirectLocation(String bucketName, String key, String newRedirectLocation)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public SetObjectLockConfigurationResult setObjectLockConfiguration(
				SetObjectLockConfigurationRequest setObjectLockConfigurationRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public SetObjectLegalHoldResult setObjectLegalHold(SetObjectLegalHoldRequest setObjectLegalHoldRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void setObjectAcl(String bucketName, String key, String versionId, CannedAccessControlList acl)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setObjectAcl(String bucketName, String key, String versionId, AccessControlList acl)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setObjectAcl(String bucketName, String key, CannedAccessControlList acl)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setObjectAcl(String bucketName, String key, AccessControlList acl)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setObjectAcl(SetObjectAclRequest setObjectAclRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setEndpoint(String endpoint) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketWebsiteConfiguration(String bucketName, BucketWebsiteConfiguration configuration)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketWebsiteConfiguration(SetBucketWebsiteConfigurationRequest setBucketWebsiteConfigurationRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketVersioningConfiguration(
				SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketTaggingConfiguration(String bucketName,
				BucketTaggingConfiguration bucketTaggingConfiguration) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketTaggingConfiguration(
				SetBucketTaggingConfigurationRequest setBucketTaggingConfigurationRequest) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketReplicationConfiguration(String bucketName, BucketReplicationConfiguration configuration)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketReplicationConfiguration(
				SetBucketReplicationConfigurationRequest setBucketReplicationConfigurationRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketPolicy(String bucketName, String policyText)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketPolicy(SetBucketPolicyRequest setBucketPolicyRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketNotificationConfiguration(String bucketName,
				BucketNotificationConfiguration bucketNotificationConfiguration)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketNotificationConfiguration(
				SetBucketNotificationConfigurationRequest setBucketNotificationConfigurationRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public SetBucketMetricsConfigurationResult setBucketMetricsConfiguration(String bucketName,
				MetricsConfiguration metricsConfiguration) throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public SetBucketMetricsConfigurationResult setBucketMetricsConfiguration(
				SetBucketMetricsConfigurationRequest setBucketMetricsConfigurationRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void setBucketLoggingConfiguration(SetBucketLoggingConfigurationRequest setBucketLoggingConfigurationRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketLifecycleConfiguration(String bucketName,
				BucketLifecycleConfiguration bucketLifecycleConfiguration) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketLifecycleConfiguration(
				SetBucketLifecycleConfigurationRequest setBucketLifecycleConfigurationRequest) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public SetBucketInventoryConfigurationResult setBucketInventoryConfiguration(String bucketName,
				InventoryConfiguration inventoryConfiguration) throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public SetBucketInventoryConfigurationResult setBucketInventoryConfiguration(
				SetBucketInventoryConfigurationRequest setBucketInventoryConfigurationRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public SetBucketEncryptionResult setBucketEncryption(SetBucketEncryptionRequest setBucketEncryptionRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void setBucketCrossOriginConfiguration(String bucketName,
				BucketCrossOriginConfiguration bucketCrossOriginConfiguration) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketCrossOriginConfiguration(
				SetBucketCrossOriginConfigurationRequest setBucketCrossOriginConfigurationRequest) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public SetBucketAnalyticsConfigurationResult setBucketAnalyticsConfiguration(String bucketName,
				AnalyticsConfiguration analyticsConfiguration) throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public SetBucketAnalyticsConfigurationResult setBucketAnalyticsConfiguration(
				SetBucketAnalyticsConfigurationRequest setBucketAnalyticsConfigurationRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void setBucketAcl(String bucketName, CannedAccessControlList acl)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketAcl(String bucketName, AccessControlList acl)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketAcl(SetBucketAclRequest setBucketAclRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketAccelerateConfiguration(String bucketName,
				BucketAccelerateConfiguration accelerateConfiguration) throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void setBucketAccelerateConfiguration(
				SetBucketAccelerateConfigurationRequest setBucketAccelerateConfigurationRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public SelectObjectContentResult selectObjectContent(SelectObjectContentRequest selectRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public RestoreObjectResult restoreObjectV2(RestoreObjectRequest request) throws AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void restoreObject(String bucketName, String key, int expirationInDays) throws AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void restoreObject(RestoreObjectRequest request) throws AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public PutObjectResult putObject(String bucketName, String key, String content)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public PutObjectResult putObject(String bucketName, String key, File file)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public PutObjectResult putObject(PutObjectRequest putObjectRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public VersionListing listVersions(String bucketName, String prefix, String keyMarker, String versionIdMarker,
				String delimiter, Integer maxResults) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public VersionListing listVersions(String bucketName, String prefix)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public VersionListing listVersions(ListVersionsRequest listVersionsRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public PartListing listParts(ListPartsRequest request) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ListObjectsV2Result listObjectsV2(String bucketName, String prefix)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ListObjectsV2Result listObjectsV2(ListObjectsV2Request listObjectsV2Request)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ListObjectsV2Result listObjectsV2(String bucketName) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ObjectListing listObjects(String bucketName, String prefix)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ObjectListing listObjects(ListObjectsRequest listObjectsRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ObjectListing listObjects(String bucketName) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public VersionListing listNextBatchOfVersions(ListNextBatchOfVersionsRequest listNextBatchOfVersionsRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public VersionListing listNextBatchOfVersions(VersionListing previousVersionListing)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ObjectListing listNextBatchOfObjects(ListNextBatchOfObjectsRequest listNextBatchOfObjectsRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ObjectListing listNextBatchOfObjects(ObjectListing previousObjectListing)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public MultipartUploadListing listMultipartUploads(ListMultipartUploadsRequest request)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<Bucket> listBuckets(ListBucketsRequest listBucketsRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<Bucket> listBuckets() throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ListBucketMetricsConfigurationsResult listBucketMetricsConfigurations(
				ListBucketMetricsConfigurationsRequest listBucketMetricsConfigurationsRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ListBucketInventoryConfigurationsResult listBucketInventoryConfigurations(
				ListBucketInventoryConfigurationsRequest listBucketInventoryConfigurationsRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ListBucketAnalyticsConfigurationsResult listBucketAnalyticsConfigurations(
				ListBucketAnalyticsConfigurationsRequest listBucketAnalyticsConfigurationsRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean isRequesterPaysEnabled(String bucketName) throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest request)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public HeadBucketResult headBucket(HeadBucketRequest headBucketRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public URL getUrl(String bucketName, String key) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Owner getS3AccountOwner(GetS3AccountOwnerRequest getS3AccountOwnerRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Owner getS3AccountOwner() throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getRegionName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Region getRegion() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetPublicAccessBlockResult getPublicAccessBlock(GetPublicAccessBlockRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetObjectTaggingResult getObjectTagging(GetObjectTaggingRequest getObjectTaggingRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetObjectRetentionResult getObjectRetention(GetObjectRetentionRequest getObjectRetentionRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ObjectMetadata getObjectMetadata(String bucketName, String key)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ObjectMetadata getObjectMetadata(GetObjectMetadataRequest getObjectMetadataRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetObjectLockConfigurationResult getObjectLockConfiguration(
				GetObjectLockConfigurationRequest getObjectLockConfigurationRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetObjectLegalHoldResult getObjectLegalHold(GetObjectLegalHoldRequest getObjectLegalHoldRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getObjectAsString(String bucketName, String key) throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public AccessControlList getObjectAcl(String bucketName, String key, String versionId)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public AccessControlList getObjectAcl(String bucketName, String key)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public AccessControlList getObjectAcl(GetObjectAclRequest getObjectAclRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public ObjectMetadata getObject(GetObjectRequest getObjectRequest, File destinationFile)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public S3Object getObject(String bucketName, String key) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public S3Object getObject(GetObjectRequest getObjectRequest) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public S3ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketWebsiteConfiguration getBucketWebsiteConfiguration(
				GetBucketWebsiteConfigurationRequest getBucketWebsiteConfigurationRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketWebsiteConfiguration getBucketWebsiteConfiguration(String bucketName)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketVersioningConfiguration getBucketVersioningConfiguration(
				GetBucketVersioningConfigurationRequest getBucketVersioningConfigurationRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketVersioningConfiguration getBucketVersioningConfiguration(String bucketName)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketTaggingConfiguration getBucketTaggingConfiguration(
				GetBucketTaggingConfigurationRequest getBucketTaggingConfigurationRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketTaggingConfiguration getBucketTaggingConfiguration(String bucketName) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketReplicationConfiguration getBucketReplicationConfiguration(
				GetBucketReplicationConfigurationRequest getBucketReplicationConfigurationRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketReplicationConfiguration getBucketReplicationConfiguration(String bucketName)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetBucketPolicyStatusResult getBucketPolicyStatus(GetBucketPolicyStatusRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketPolicy getBucketPolicy(GetBucketPolicyRequest getBucketPolicyRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketPolicy getBucketPolicy(String bucketName) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketNotificationConfiguration getBucketNotificationConfiguration(
				GetBucketNotificationConfigurationRequest getBucketNotificationConfigurationRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketNotificationConfiguration getBucketNotificationConfiguration(String bucketName)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetBucketMetricsConfigurationResult getBucketMetricsConfiguration(String bucketName, String id)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetBucketMetricsConfigurationResult getBucketMetricsConfiguration(
				GetBucketMetricsConfigurationRequest getBucketMetricsConfigurationRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketLoggingConfiguration getBucketLoggingConfiguration(
				GetBucketLoggingConfigurationRequest getBucketLoggingConfigurationRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketLoggingConfiguration getBucketLoggingConfiguration(String bucketName)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getBucketLocation(GetBucketLocationRequest getBucketLocationRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getBucketLocation(String bucketName) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketLifecycleConfiguration getBucketLifecycleConfiguration(
				GetBucketLifecycleConfigurationRequest getBucketLifecycleConfigurationRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketLifecycleConfiguration getBucketLifecycleConfiguration(String bucketName) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetBucketInventoryConfigurationResult getBucketInventoryConfiguration(String bucketName, String id)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetBucketInventoryConfigurationResult getBucketInventoryConfiguration(
				GetBucketInventoryConfigurationRequest getBucketInventoryConfigurationRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetBucketEncryptionResult getBucketEncryption(GetBucketEncryptionRequest request)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetBucketEncryptionResult getBucketEncryption(String bucketName)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(
				GetBucketCrossOriginConfigurationRequest getBucketCrossOriginConfigurationRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(String bucketName) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetBucketAnalyticsConfigurationResult getBucketAnalyticsConfiguration(String bucketName, String id)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public GetBucketAnalyticsConfigurationResult getBucketAnalyticsConfiguration(
				GetBucketAnalyticsConfigurationRequest getBucketAnalyticsConfigurationRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public AccessControlList getBucketAcl(GetBucketAclRequest getBucketAclRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public AccessControlList getBucketAcl(String bucketName) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketAccelerateConfiguration getBucketAccelerateConfiguration(
				GetBucketAccelerateConfigurationRequest getBucketAccelerateConfigurationRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public BucketAccelerateConfiguration getBucketAccelerateConfiguration(String bucketName)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public URL generatePresignedUrl(String bucketName, String key, Date expiration, HttpMethod method)
				throws SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public URL generatePresignedUrl(String bucketName, String key, Date expiration) throws SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public URL generatePresignedUrl(GeneratePresignedUrlRequest generatePresignedUrlRequest) throws SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void enableRequesterPays(String bucketName) throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void download(PresignedUrlDownloadRequest presignedUrlDownloadRequest, File destinationFile) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public PresignedUrlDownloadResult download(PresignedUrlDownloadRequest presignedUrlDownloadRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean doesObjectExist(String bucketName, String objectName)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean doesBucketExistV2(String bucketName) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean doesBucketExist(String bucketName) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void disableRequesterPays(String bucketName) throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteVersion(String bucketName, String key, String versionId)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteVersion(DeleteVersionRequest deleteVersionRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public DeletePublicAccessBlockResult deletePublicAccessBlock(DeletePublicAccessBlockRequest request) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public DeleteObjectsResult deleteObjects(DeleteObjectsRequest deleteObjectsRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public DeleteObjectTaggingResult deleteObjectTagging(DeleteObjectTaggingRequest deleteObjectTaggingRequest) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void deleteObject(String bucketName, String key) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteObject(DeleteObjectRequest deleteObjectRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteBucketWebsiteConfiguration(
				DeleteBucketWebsiteConfigurationRequest deleteBucketWebsiteConfigurationRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteBucketWebsiteConfiguration(String bucketName) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteBucketTaggingConfiguration(
				DeleteBucketTaggingConfigurationRequest deleteBucketTaggingConfigurationRequest) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteBucketTaggingConfiguration(String bucketName) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteBucketReplicationConfiguration(DeleteBucketReplicationConfigurationRequest request)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteBucketReplicationConfiguration(String bucketName)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteBucketPolicy(DeleteBucketPolicyRequest deleteBucketPolicyRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteBucketPolicy(String bucketName) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public DeleteBucketMetricsConfigurationResult deleteBucketMetricsConfiguration(String bucketName, String id)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public DeleteBucketMetricsConfigurationResult deleteBucketMetricsConfiguration(
				DeleteBucketMetricsConfigurationRequest deleteBucketMetricsConfigurationRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void deleteBucketLifecycleConfiguration(
				DeleteBucketLifecycleConfigurationRequest deleteBucketLifecycleConfigurationRequest) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteBucketLifecycleConfiguration(String bucketName) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public DeleteBucketInventoryConfigurationResult deleteBucketInventoryConfiguration(String bucketName, String id)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public DeleteBucketInventoryConfigurationResult deleteBucketInventoryConfiguration(
				DeleteBucketInventoryConfigurationRequest deleteBucketInventoryConfigurationRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public DeleteBucketEncryptionResult deleteBucketEncryption(DeleteBucketEncryptionRequest request)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public DeleteBucketEncryptionResult deleteBucketEncryption(String bucketName)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void deleteBucketCrossOriginConfiguration(
				DeleteBucketCrossOriginConfigurationRequest deleteBucketCrossOriginConfigurationRequest) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteBucketCrossOriginConfiguration(String bucketName) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public DeleteBucketAnalyticsConfigurationResult deleteBucketAnalyticsConfiguration(String bucketName, String id)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public DeleteBucketAnalyticsConfigurationResult deleteBucketAnalyticsConfiguration(
				DeleteBucketAnalyticsConfigurationRequest deleteBucketAnalyticsConfigurationRequest)
				throws AmazonServiceException, SdkClientException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void deleteBucket(String bucketName) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void deleteBucket(DeleteBucketRequest deleteBucketRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public Bucket createBucket(String bucketName, String region) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Bucket createBucket(String bucketName, Region region) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Bucket createBucket(String bucketName) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Bucket createBucket(CreateBucketRequest createBucketRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public CopyPartResult copyPart(CopyPartRequest copyPartRequest) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public CopyObjectResult copyObject(String sourceBucketName, String sourceKey, String destinationBucketName,
				String destinationKey) throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void changeObjectStorageClass(String bucketName, String key, StorageClass newStorageClass)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void abortMultipartUpload(AbortMultipartUploadRequest request)
				throws SdkClientException, AmazonServiceException {
			// TODO Auto-generated method stub
			
		}
	};
	private final S3Componet componet = new S3Componet();

	public void uploadFileS3( File fileData ,String fileName)
	{
		amazonS3.putObject(componet.getBucket(),fileName,fileData);
	}
	public String getFileUrl(String fileName)
	{
		return amazonS3.getUrl(componet.getBucket(), fileName).toString();
	}
	// 파일을 저장해주는 함수 
    // 받은 파일을 SAVE_PATH위치에 임의의 이름으로 저장하고
    // 저장된 이름을 리턴한다.
    // TODO: 중복이 아예 일어날 수 없게 설계되거나 중복시 다시 시도 할 수 있게 짜야함(재귀)
    public String fileSave(List<MultipartFile> files ,String SAVE_PATH,String where) throws IOException
    {
    	FileOutputStream fos = null;

    	  
    	System.out.println(files.size());
    	String imageurl = "";
    	for(int i=0;i<files.size();i++)
    	{
			byte[] fileData = files.get(i).getBytes();
			String uuid = UUID.randomUUID().toString();
			try {
			fos = new FileOutputStream(SAVE_PATH+"test"+uuid+".png");
			imageurl+=uuid+",";
			if(where.equals("s3"))
			{
				fileDelete(imageurl,SAVE_PATH);	
				File convFile = new File( files.get(i).getOriginalFilename());
				files.get(i).transferTo(convFile);
				uploadFileS3(convFile, imageurl);
			}
			}
			catch (Exception e) {
				System.err.println(e);
				fos = new FileOutputStream(SAVE_PATH+"test1"+uuid+".png");
				imageurl=uuid+",";
			}
			fos.write(fileData);	
    	}
    	fos.close();
    	return imageurl;
    }
    public boolean fileDelete(String imageName,String SAVE_PATH)
    {
    	String[] imageUrl = imageName.split(",");
    	boolean result = false;
    	for(String url:imageUrl)
    	{
    		if(url!=null)
    		{
    			File img = new File(SAVE_PATH+url+".png");
	    		result = img.delete();
    		}
    	}
    	return result;
    }
    //중복제거 사이즈 컬러 수량
    public Map<String,String> anyArray(List<ProductVO> sql,String type)
    {
		Map<String,String> anyData = new HashMap<String,String>();
		for(int j = 0; j<sql.size();j++)
		{
			if(type == "size")
				anyData.put(sql.get(j).getSize(), sql.get(j).getSize());
			if(type == "color")
				anyData.put(sql.get(j).getColor(), sql.get(j).getColor());
			if(type == "quantity")
				anyData.put(sql.get(j).getQuantity(), sql.get(j).getQuantity());			
		}
		return anyData;
    }  
    //사이즈 변환 배열
    public String sizes[] = {"XS","S","M","L","XL","2XL","3XL","4XL","5XL","6XL","7XL",
    								"XS(80)","S(85)","M(90)","L(95)","XL(100)","2XL(105)","3XL(110)","4XL(115)","5XL(120)",
    								"XS(85)","S(90)","M(95)","L(100)","XL(105)","2XL(110)","3XL(115)","4XL(120)","5XL(125)",
    								"XS(90)","S(95)","M(100)","L(105)","XL(110)","2XL(115)","3XL(120)","4XL(125)","5XL(130)",
    								"85","90","95","100","105","110","115","120","125","130","FREE"};
    public String sizeString(Map<String,String> size)
    {
    	
    	String result = "";
    	String endSize = "";
    	boolean addSize = false;
    	for(String s: sizes)
    	{
    		
    		for(String sizeList:size.values())
    		{
    			if(sizeList.equals(s))
    				endSize=s;
    			if(sizeList.equals(s)&&addSize==false)
    			{	
    				addSize=!addSize;
    				result=result+s;  				
    			}
    		}
    	}
    	if(!result.equals(endSize))
    		result = result+" ~ "+endSize;
    	return result;
    }
    public JSONObject sizeArray(String size) {
    	JSONArray Jarr = new JSONArray();
    	JSONObject Jobj = new JSONObject();
    	boolean addSize = false;
    	for(String s : sizes)
    	{
    		//시작 지점 찾기
    		if(size.contains(s+" ~"))
    		{
//    			System.out.println("contains:"+s);
    			addSize=!addSize;
    		}
    		//범위 데이터 등록
    		if(addSize)
    		{
//    			System.out.println(s);
    			Jarr.add(s);
    			
    		}
    		//끝 지점 찾기
    		if(addSize&&size.contains("~ "+s))
    		{
//    			System.out.println("contains:"+s);
    			addSize=!addSize;
    		}
    	}
    	if(Jarr.isEmpty())
		{
			Jarr.add("FREE");
		}
    	Jobj.put("size", Jarr);
		return Jobj;
    }
    
    public ArrayList<String> sizeArray1(String size) {
    	boolean addSize = false;
    	ArrayList<String> size1 = new ArrayList<String>();
    	for(String s : sizes)
    	{
    		//시작 지점 찾기
    		if(size.contains(s+" ~"))
    		{
//    			System.out.println("contains:"+s);
    			addSize=!addSize;
    		}
    		//범위 데이터 등록
    		if(addSize)
    		{
//    			System.out.println(s);
    			size1.add(s);		
    		}
    		//끝 지점 찾기
    		if(addSize&&size.contains("~ "+s))
    		{
//    			System.out.println("contains:"+s);
    			addSize=!addSize;
    		}
    	}
    	if(size1.isEmpty())
		{
    		size1.add("FREE");
		}
    
		return size1;
    }
    
    public String key ="11";  // 따로 저장하고 .gitignore에 제외항목에 등록시킬것.!
    public String makeJwtToken(String id, String password) {
        Date now = new Date();
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
            .setIssuer("kim") // (2) 발급자
            .setIssuedAt(now) // (3) 
            .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // (4) 만료시간
            .claim("id", id) // (5)
            .signWith(SignatureAlgorithm.HS256, key.getBytes()) // (6) 암호화 키 (노출되면 안된다.!)
            .compact();
      }
    public Claims parseringJwtToken(String jwtToken) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException
    {
    	return Jwts.parser()
    		.setSigningKey(key.getBytes("UTF-8"))
    		.parseClaimsJws(jwtToken)
    		.getBody();
    }
 // 이메일 난수 만드는 메서드
 	public String init(boolean lowerCheck,int size) {
 		Random ran = new Random();
 		StringBuffer sb = new StringBuffer();
 		int num = 0;

 		do {
 			num = ran.nextInt(75) + 48;
 			if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
 				sb.append((char) num);
 			} else {
 				continue;
 			}

 		} while (sb.length() < size);
 		if (lowerCheck) {
 			return sb.toString().toLowerCase();
 		}
 		return sb.toString();
 	}
}

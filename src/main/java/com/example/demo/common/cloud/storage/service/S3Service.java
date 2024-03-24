package com.example.demo.common.cloud.storage.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final Map<String, AmazonS3> s3ClientPool = new HashMap<>();

    public AmazonS3 getClient(String regionId) {
        // 매번 Client를 만들기 보다 Connection 재사용
        if (s3ClientPool.containsKey(regionId)) {
            return s3ClientPool.get(regionId);
        } else {
            AmazonS3 s3Client = AmazonS3ClientBuilder
                    .standard()
                    .withRegion(regionId)
                    .build();
            s3ClientPool.put(regionId, s3Client);
            return s3Client;
        }
    }

    public URL getDownloadUrl(String regionId, String bucketName, String uri, long expireSeconds) {
        try {
            return createPreSignedUrl(HttpMethod.GET, regionId, bucketName, uri, expireSeconds);
        } catch (Exception exception) {
            log.error("S3 file download url request failed: {}", uri, exception);
            throw exception;
        }
    }

    public URL getUploadUrl(String regionId, String bucketName, String uri, long expireSeconds) {
        try {
            return createPreSignedUrl(HttpMethod.PUT, regionId, bucketName, uri, expireSeconds);
        } catch (Exception exception) {
            log.error("S3 file upload url request failed: {}", uri, exception);
            throw exception;
        }
    }

    public boolean isS3FileExist(String regionId, String bucketName, String uri) {
        try {
            throwExceptionIfParameterBlank(regionId, bucketName, uri);
            AmazonS3 s3Client = getClient(regionId);
            return s3Client.doesObjectExist(bucketName, uri);
        } catch (AmazonS3Exception exception) {
            if (exception.getStatusCode() == 404) {
                log.warn("S3 file Path not found: {}", uri);
                return false;
            } else {
                log.error("S3 file exist check failed: {}", uri, exception);
                throw exception;
            }
        } catch (Exception exception) {
            log.error("S3 file exist check failed: {}", uri, exception);
            throw exception;
        }
    }


    @Async
    public void deleteS3File(String regionId, String bucketName, String uri) {
        try {
            throwExceptionIfParameterBlank(regionId, bucketName, uri);
            AmazonS3 s3Client = getClient(regionId);
            s3Client.deleteObject(bucketName, uri);
        } catch (Exception exception) {
            log.error("S3 file delete failed: {}", uri, exception);
            throw exception;
        }
    }


    private URL createPreSignedUrl(HttpMethod method, String regionId, String bucketName, String uri, long expireSeconds) {

        throwExceptionIfParameterBlank(regionId, bucketName, uri);

        AmazonS3 s3Client = getClient(regionId);

        Date expirationDate = new Date(Instant.now().toEpochMilli() + expireSeconds * 1000L);
        GeneratePresignedUrlRequest request =
                new GeneratePresignedUrlRequest(bucketName, uri, method).withExpiration(expirationDate);

        return s3Client.generatePresignedUrl(request);
    }


    private void throwExceptionIfParameterBlank(String regionId, String bucketName, String uri) {
        if (StringUtils.isBlank(regionId) || StringUtils.isBlank(bucketName) || StringUtils.isBlank(uri)) {
            throw new IllegalArgumentException(String.format("S3 file path is invalid: regionId=%s, bucketName=%s, uri=%s", regionId, bucketName, uri));
        }
    }

}

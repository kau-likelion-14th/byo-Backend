package likelion14th.lte.global.config;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class AmazonConfig {
    //todo s3 세션떄 주석 해제 할 것
    /*
    private final String bucket;
    private final String accessKey;
    private final String secretKey;
    private final String region;
    private final String locationPath;

    public AmazonConfig(
            @Value("${cloud.aws.s3.bucket}") String bucket,
            @Value("${cloud.aws.credentials.access-key}") String accessKey,
            @Value("${cloud.aws.credentials.secret-key}") String secretKey,
            @Value("${cloud.aws.region}") String region,
            @Value("${cloud.aws.s3.path.location}") String locationPath
    ) {
        this.bucket = bucket;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
        this.locationPath = locationPath;
    }

    @Bean S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .build();
    }

 */
}

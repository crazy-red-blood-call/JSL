package com.antlord.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * ElasticSearch 配置
 * </p>
 *
 * @author yangyunjun
 * @since 2021-07-15
 */
@Slf4j
@Configuration
public class ElasticSearchClientConfig {

    @Value("${spring.data.elasticsearch.host:127.0.0.1:9200}")
    private String host;

    @Value("${spring.data.elasticsearch.username:elastic}")
    private String username;

    @Value("${spring.data.elasticsearch.password:szwg%2021}")
    private String password;

    @Value("${spring.data.elasticsearch.connectNum:200}")
    private Integer connectNum;

    @Value("${spring.data.elasticsearch.connectPerRoute:16}")
    private Integer connectPerRoute;

    @Value("${spring.data.elasticsearch.connectTimeout:120000}")
    private Integer connectTimeout;

    @Value("${spring.data.elasticsearch.socketTimeout:120000}")
    private Integer socketTimeout;

    @Value("${spring.data.elasticsearch.requestTimeout:120000}")
    private Integer requestTimeout;

    @Value("${spring.data.elasticsearch.keepAlive:60000}")
    private Integer keepAlive;

    @Bean
    public RestHighLevelClient elasticSearchClient() {
        RestHighLevelClient client = null;
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));
        try {
            client = new RestHighLevelClient(
                    RestClient.builder(
                                    new HttpHost(host.split(":")[0], Integer.parseInt(host.split(":")[1]), "http")
                            ).setRequestConfigCallback(builder -> builder.setConnectTimeout(connectTimeout)
                                    .setSocketTimeout(socketTimeout).setConnectionRequestTimeout(requestTimeout))
                            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                                    .setMaxConnTotal(connectNum)
                                    .setMaxConnPerRoute(connectPerRoute)
                                    .setKeepAliveStrategy((httpRequest, httpResponse) -> keepAlive)
                                    .setDefaultCredentialsProvider(credentialsProvider)));

            log.info("es服务器:" + host + "/" + username + "/" + password + " 已连接");
        } catch (Exception e) {
            log.error("==>RestHighLevelClient init found error:{}", e);
        }
        return client;
    }

    /**
     * 重新链接es
     * @return
     */
    public RestHighLevelClient getClient() {
        RestHighLevelClient client = null;
        try {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(username, password));
            client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(host.split(":")[0], Integer.parseInt(host.split(":")[1]), "http")
                    ).setRequestConfigCallback(builder -> builder.setConnectTimeout(connectTimeout)
                            .setSocketTimeout(socketTimeout).setConnectionRequestTimeout(requestTimeout))
                            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                                    .setMaxConnTotal(connectNum)
                                    .setMaxConnPerRoute(connectPerRoute)
                                    .setKeepAliveStrategy((httpRequest, httpResponse) -> keepAlive)
                                    .setDefaultCredentialsProvider(credentialsProvider)));

            log.info("es服务器:" + host + "/" + username + "/" + password + " 重新连接");
        } catch (Exception e) {
            log.error("==>RestHighLevelClient init found error:{}", e);
        }
//        try {
//            HttpHost httpHost = new HttpHost(host.split(":")[0], Integer.parseInt(host.split(":")[1]), HttpHost.DEFAULT_SCHEME_NAME);
//            RestClientBuilder builder = RestClient.builder(httpHost);
//            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
//            builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));
//            client = new RestHighLevelClient(builder);
//
//        } catch (Exception e) {
//            log.error("==>RestHighLevelClient init found error:{}", e);
//        }
        return client;
    }

    /**
     * 重新链接es
     * @return
     */
    public RestHighLevelClient getClientNoTimeout() {
        RestHighLevelClient client = null;
        try {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(username, password));
            client = new RestHighLevelClient(
                    RestClient.builder(
                                    new HttpHost(host.split(":")[0], Integer.parseInt(host.split(":")[1]), "http")
                            ).setRequestConfigCallback(builder -> builder.setConnectTimeout(connectTimeout * 100)
                                    .setSocketTimeout(socketTimeout * 100).setConnectionRequestTimeout(requestTimeout * 100))
                            .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                                    .setMaxConnTotal(connectNum)
                                    .setMaxConnPerRoute(connectPerRoute)
                                    .setKeepAliveStrategy((httpRequest, httpResponse) -> keepAlive * 100)
                                    .setDefaultCredentialsProvider(credentialsProvider)));

            log.info("es服务器:" + host + "/" + username + "/" + password + " 统计海量数据");
        } catch (Exception e) {
            log.error("==>RestHighLevelClient init found error:{}", e);
        }
        return client;
    }
}

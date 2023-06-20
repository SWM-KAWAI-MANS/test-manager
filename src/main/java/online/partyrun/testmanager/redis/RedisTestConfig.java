package online.partyrun.testmanager.redis;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import redis.embedded.RedisServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Objects;

@Configuration
public class RedisTestConfig {

    private int redisPort;

    public RedisTestConfig(@Value("${spring.data.redis.port}") Integer redisPort,
                           @Value("${spring.data.redis.uri}") String redisUri) {
        this.redisPort = getMainPort(redisPort, redisUri);
    }

    private int getMainPort(Integer redisPort, String redisUri) {
        if (Objects.nonNull(redisPort)) {
            return redisPort;
        }

        if (StringUtils.hasText(redisUri)) {
            return URI.create(redisUri).getPort();
        }

        return 6379;
    }

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws IOException {
        final int port = getTestServerPort();
        redisServer = new RedisServer(port);
        redisServer.start();
    }

    private int getTestServerPort() throws IOException {
        final Process redisProcess = executeGrepProcessCommand(redisPort);

        if (isRunning(redisProcess)) {
            return findAvailablePort();
        }

        return redisPort;
    }

    /**
     * 해당 port를 사용중인 프로세스 확인하는 sh 실행
     */
    private Process executeGrepProcessCommand(int port) throws IOException {
        final String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
        final String[] shell = {"/bin/sh", "-c", command};
        return Runtime.getRuntime().exec(shell);
    }

    /**
     * 해당 Process가 현재 실행중인지 확인
     */
    private boolean isRunning(Process process) {
        final StringBuilder pidInfo = new StringBuilder();
        String line;

        try (BufferedReader input =
                     new BufferedReader(new InputStreamReader(process.getInputStream()))) {

            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }

        } catch (Exception e) {
        }

        return StringUtils.hasText(pidInfo.toString());
    }

    /**
     * 현재 PC/서버에서 사용가능한 포트 조회
     */
    private int findAvailablePort() throws IOException {

        for (int port = 10000; port <= 65535; port++) {
            final Process process = executeGrepProcessCommand(port);
            if (!isRunning(process)) {
                return port;
            }
        }

        throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}

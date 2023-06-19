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

@Configuration
public class RedisTestConfig {

    @Value("${spring.data.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws IOException {
        final int port = getPort();
        redisServer = new RedisServer(port);
        redisServer.start();
    }

    private int getPort() throws IOException {
        final Process redisProcess = executeGrepProcessCommand(redisPort);

        if (isRunning(redisProcess)) {
            return findAvailablePort();
        }

        return redisPort;
    }

    /** 해당 port를 사용중인 프로세스 확인하는 sh 실행 */
    private Process executeGrepProcessCommand(int port) throws IOException {
        final String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
        final String[] shell = {"/bin/sh", "-c", command};
        return Runtime.getRuntime().exec(shell);
    }

    /** 해당 Process가 현재 실행중인지 확인 */
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

    /** 현재 PC/서버에서 사용가능한 포트 조회 */
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

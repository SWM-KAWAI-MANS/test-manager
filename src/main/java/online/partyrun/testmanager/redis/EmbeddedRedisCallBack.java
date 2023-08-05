package online.partyrun.testmanager.redis;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmbeddedRedisCallBack implements BeforeAllCallback, AfterAllCallback {
    private static final int EMBEDDED_REDIS_PORT = 16379;

    RedisServer redisServer;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        final Process redisProcess = executeGrepProcessCommand(EMBEDDED_REDIS_PORT);

        if (!isRunning(redisProcess)) {
            redisServer = new RedisServer(EMBEDDED_REDIS_PORT);
            redisServer.start();
        }
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

    @Override
    public void afterAll(ExtensionContext context) {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}

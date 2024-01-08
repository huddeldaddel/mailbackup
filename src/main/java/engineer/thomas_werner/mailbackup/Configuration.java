package engineer.thomas_werner.mailbackup;

import java.util.Optional;

/**
 * POJO that contains the configuration of the IMAP connection.
 *
 * @author Thomas Werner
 */
public class Configuration {

    private String host;
    private String user;
    private String password;
    private Optional<Integer> port = Optional.empty();
    private boolean ssl;

    public Configuration() {
        super();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Optional<Integer> getPort() {
        return port;
    }

    public void setPort(final Integer port) {
        this.port = Optional.ofNullable(port);
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

}

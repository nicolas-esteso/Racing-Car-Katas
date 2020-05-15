package tddmicroexercises.telemetrysystem;

public class TelemetryClientMock implements ITelemetryClient {

    private int connectionFailedCounter = 0;
    private int failuresBeforeConnection = 0;
    private boolean onlineStatus = false;
    private final String receivedData;
    private String sentData;
    private String lastServerConnectionData;
    private int connectionCallCount = 0;
    private boolean hasDisconnectBeenCalled;

    /**
     * Constructor.
     *
     * @param failuresBeforeConnection number of times the connect method will fail to actually connect to the server,
     *                                 leaving the onlineStatus boolean to false. All next attempts to call the connect
     *                                 method will succeed and set the onlineStatus boolean to true.
     * @param receivedData             mock for the response to the receive method.
     */
    TelemetryClientMock(int failuresBeforeConnection, String receivedData) {
        this.failuresBeforeConnection = failuresBeforeConnection;
        this.receivedData = receivedData;
    }

    public String getSentData() {
        return sentData;
    }

    public String getLastServerConnectionData() {
        return lastServerConnectionData;
    }

    public int getConnectionCallCount() {
        return connectionCallCount;
    }

    public boolean isHasDisconnectBeenCalled() {
        return hasDisconnectBeenCalled;
    }

    @Override
    public boolean getOnlineStatus() {
        return this.onlineStatus;
    }

    @Override
    public void connect(String telemetryServerConnectionString) {
        connectionCallCount++;
        if (failuresBeforeConnection > connectionFailedCounter) {
            connectionFailedCounter++;
            onlineStatus = false;
        } else {
            onlineStatus = true;
        }

        this.lastServerConnectionData = telemetryServerConnectionString;
    }

    @Override
    public void disconnect() {
        this.hasDisconnectBeenCalled = true;
    }

    @Override
    public void send(String message) {
        sentData = message;
    }

    @Override
    public String receive() {
        return receivedData;
    }
}

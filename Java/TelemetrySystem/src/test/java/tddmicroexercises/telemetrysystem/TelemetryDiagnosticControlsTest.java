package tddmicroexercises.telemetrysystem;

import org.junit.Assert;
import org.junit.Test;

public class TelemetryDiagnosticControlsTest
{
    
	@Test
    public void CheckTransmission_should_send_a_diagnostic_message_and_receive_a_status_message_response() throws Exception
    {
        TelemetryClientMock telemetryClientMock = new TelemetryClientMock(0, "data");
        TelemetryDiagnosticControls telemetryDiagnosticControls = new TelemetryDiagnosticControls(telemetryClientMock);
        telemetryDiagnosticControls.checkTransmission();

        Assert.assertTrue(telemetryClientMock.isHasDisconnectBeenCalled());
        Assert.assertEquals(TelemetryDiagnosticControls.DIAGNOSTIC_CHANNEL_CONNECTION_STRING, telemetryClientMock.getLastServerConnectionData());
        Assert.assertEquals(ITelemetryClient.DIAGNOSTIC_MESSAGE, telemetryClientMock.getSentData());
        Assert.assertEquals("data", telemetryDiagnosticControls.getDiagnosticInfo());
    }

    @Test
    public void CheckTransmission_should_not_fail_if_client_is_not_online_after_1_retry() throws Exception
    {
        TelemetryClientMock telemetryClientMock = new TelemetryClientMock(1, null);
        TelemetryDiagnosticControls telemetryDiagnosticControls = new TelemetryDiagnosticControls(telemetryClientMock);
        telemetryDiagnosticControls.checkTransmission();

        Assert.assertTrue(telemetryClientMock.isHasDisconnectBeenCalled());
        Assert.assertEquals(2, telemetryClientMock.getConnectionCallCount());
    }

    @Test
    public void CheckTransmission_should_not_fail_if_client_is_not_online_after_2_retries() throws Exception
    {
        TelemetryClientMock telemetryClientMock = new TelemetryClientMock(2, null);
        TelemetryDiagnosticControls telemetryDiagnosticControls = new TelemetryDiagnosticControls(telemetryClientMock);
        telemetryDiagnosticControls.checkTransmission();

        Assert.assertTrue(telemetryClientMock.isHasDisconnectBeenCalled());
        Assert.assertEquals(3, telemetryClientMock.getConnectionCallCount());
    }

    @Test
    public void CheckTransmission_should_fail_if_client_is_not_online_after_3_retries()
    {
        TelemetryClientMock telemetryClientMock = new TelemetryClientMock(3, null);
        try
        {
            TelemetryDiagnosticControls telemetryDiagnosticControls = new TelemetryDiagnosticControls(telemetryClientMock);
            telemetryDiagnosticControls.checkTransmission();
            Assert.fail();
        }
        catch (Exception e)
        {
            Assert.assertTrue(telemetryClientMock.isHasDisconnectBeenCalled());
            Assert.assertEquals(3, telemetryClientMock.getConnectionCallCount());
            Assert.assertNull(telemetryClientMock.getSentData());
        }
    }
}

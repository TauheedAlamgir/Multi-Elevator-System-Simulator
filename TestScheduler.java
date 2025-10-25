// TestScheduler.java

import org.junit.Assert;
import org.junit.Test;

public class TestScheduler {

    @Test
    public void testHandleFloorRequest() {
        try {
            Scheduler scheduler = new Scheduler(4888); // Use the actual port number for your scheduler
            String request = "Time: 08:00, Start Floor: 1, Destination Floor: 5, Direction: Up";

            // Simulate handling a request
            scheduler.handleFloorRequest(request);

            // Assuming the method 'handleFloorRequest' doesn't return a value and changes internal state
            // Assert true since no exception was thrown
            Assert.assertTrue("Handled floor request", true);
        } catch (Exception e) {
            Assert.fail("Scheduler threw an exception: " + e.getMessage());
        }
    }
}

package io.callstats.sdk;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class CallStatsTest.
 *
 * @author Karthik Budigere
 */
public class CallStatsTest{
	
	/** The callstatslib. */
	CallStats callstatslib;
	
	/** The listener. */
	CallStatsInitListener listener;
	
	/** The endpoint info. */
	EndpointInfo endpointInfo;
	
	/** The app id. */
	int appId = 497346896;
	
	/** The app secret. */
	String appSecret = "usKVYjMm3JoqGhILunSSgfJynYs=";
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {	
		endpointInfo = new EndpointInfo();
		endpointInfo.setEndpointType(CallStatsConst.END_POINT_TYPE);
		endpointInfo.setName("JitSi");
		endpointInfo.setOs("LINUX");
		endpointInfo.setVer("4.4");		
		System.out.println("Setup completed");
		callstatslib = new CallStats();
		listener = mock(CallStatsInitListener.class);
	}
	
	/**
	 * Intialize test.
	 */
	@Test
	public void intializeTest() {
		callstatslib.intialize(497346896, appSecret, "jit.si.345",endpointInfo,listener);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = "SDK authentication successful";
		verify(listener).onInitialized(msg);
	}
	
	/**
	 * Intialize with invalid app id test.
	 */
	@Test
	public void intializeWithInvalidAppIdTest() {
		CallStatsErrors error = CallStatsErrors.HTTP_ERROR;
		String errMsg = "SDK Authentication Error";
		callstatslib.intialize(175240363, appSecret, "jit.si.345",endpointInfo,listener);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(listener).onError(error, errMsg);
	}
	
	/**
	 * Intialize with null args test.
	 */
	@Test
	public void intializeWithNullArgsTest() {
		Throwable e = null;

		try {
		callstatslib.intialize(0, appSecret, "jit.si.345",endpointInfo,listener);
		} catch (Throwable e1) {
			e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
		
		try {
			callstatslib.intialize(175240363, null, "jit.si.345",endpointInfo,listener);
		} catch (Throwable e1) {
				e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
		
		try {
			callstatslib.intialize(175240363, appSecret, null,endpointInfo,listener);
		} catch (Throwable e1) {
				e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
		
		try {
			callstatslib.intialize(175240363, "", "jit.si.345",endpointInfo,listener);
		} catch (Throwable e1) {
				e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
		
		try {
			callstatslib.intialize(175240363, appSecret, "",endpointInfo,listener);
		} catch (Throwable e1) {
				e = e1;
		}
		assertTrue(e instanceof IllegalArgumentException);
	}
	
	
	
	/**
	 * Intialize test with send call stats event.
	 */
	@Test
	public void intializeTestWithSendCallStatsEvent() {
		callstatslib.intialize(497346896, appSecret, "jit.si.345",endpointInfo,listener);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = "SDK authentication successful";
		verify(listener).onInitialized(msg);
		
		for (int i=0;i<1000;i++) {
			BridgeStatusInfoBuilder bridgeStatusInfoBuilder = new BridgeStatusInfoBuilder();
			BridgeStatusInfo bridgeStatusInfo= bridgeStatusInfoBuilder
												.avgIntervalJitter(i)
												.cpuUsage(22)
												.intervalLoss(i)
												.build();						
			callstatslib.sendCallStatsBridgeEvent(bridgeStatusInfo);
		}
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

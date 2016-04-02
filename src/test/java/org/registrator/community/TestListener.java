package org.registrator.community;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


/**
 * Created by roman.golyuk on 15.03.2016.
 */
public class TestListener extends TestListenerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger("");
    private static final String LOG_MSG_BEGIN = "begin";

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        LOG.info("<<SUCCESS>> " + tr.getName());
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        LOG.info("<<FAILED>> " + tr.getName());
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        LOG.info("<<SKIPPED>> " + tr.getName());
    }
}


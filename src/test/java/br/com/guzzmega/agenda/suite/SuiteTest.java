package br.com.guzzmega.agenda.suite;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Tests Suite")
@SelectPackages(value = {
        "br.com.guzzmega.agenda.domain",
        "br.com.guzzmega.agenda.service",
        "br.com.guzzmega.agenda.controller"
})
public class SuiteTest {
}

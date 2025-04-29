package com.musinsa.subject.controller

import com.musinsa.subject.service.facade.BrandFacade
import org.spockframework.spring.SpringBean
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Narrative
import spock.lang.Specification

@WebMvcTest(controllers = [BrandController])
@ContextConfiguration(classes = BrandController)
@Narrative("Brand Controller Tests")
class BrandControllerSpec extends Specification {

    @SpringBean
    BrandFacade brandFacade = Mock()

}

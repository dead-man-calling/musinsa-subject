package com.musinsa.subject.controller

import com.musinsa.subject.service.facade.PriceFacade
import org.spockframework.spring.SpringBean
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Narrative

@WebMvcTest(controllers = [PriceController])
@ContextConfiguration(classes = PriceController)
@Narrative("Price Controller Tests")
class PriceControllerSpec {

    @SpringBean
    PriceFacade priceFacade = Mock()

}

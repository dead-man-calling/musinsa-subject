package com.musinsa.subject.controller

import com.musinsa.subject.service.facade.ProductFacade
import org.spockframework.spring.SpringBean
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Narrative

@WebMvcTest(controllers = [ProductController])
@ContextConfiguration(classes = ProductController)
@Narrative("Product Controller Tests")
class ProductControllerSpec {

    @SpringBean
    ProductFacade productFacade = Mock()

}

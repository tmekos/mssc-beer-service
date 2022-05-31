package meko.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import meko.springframework.msscbeerservice.bootstrap.BeerLoader;
import meko.springframework.msscbeerservice.services.BeerService;
import meko.springframework.msscbeerservice.web.model.BeerDto;
import meko.springframework.msscbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@ExtendWith(RestDocumentationExtension.class)
//@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.meko", uriPort = 80)
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "meko.springframework.msscbeerservice.web.mappers")
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Test
    void getBeerById() throws Exception {

        given(beerService.getById(any())).willReturn(getValidBeerDto());

        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void saveNewBeer() throws Exception {

        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        given(beerService.saveNewBeer(any())).willReturn(getValidBeerDto());

        mockMvc.perform(post("/api/v1/beer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception {
        given(beerService.updateBeer(any(), any())).willReturn(getValidBeerDto());

        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    BeerDto getValidBeerDto(){
        return BeerDto.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("22.99"))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }
}
//class BeerControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockBean
//    BeerService beerService;
//
//    @Test
//    void getBeerById() throws Exception {
//        given(beerService.findById(any())).willReturn(getValidBeerDto());
//
//        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID().toString())
//                        //.param("iscold", "yes")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
////                .andDo(document("v1/beer-get",
////                        pathParameters(
////                            parameterWithName("beerId").description("UUID of desired beer to get.")
////                        ),
////                        requestParameters(
////                                parameterWithName("iscold").description("Is Beer Cold Query param")
////                        ),
////                        responseFields(
////                                fieldWithPath("id").description("id of beer"),
////                                fieldWithPath("version").description("version"),
////                                fieldWithPath("createdDate").description("Date Created"),
////                                fieldWithPath("lastModifiedDate").description("Date Updated"),
////                                fieldWithPath("beerName").description("Beer Name"),
////                                fieldWithPath("beerStyle").description("Beer Style"),
////                                fieldWithPath("upc").description("UPC of Beer"),
////                                fieldWithPath("price").description("Price"),
////                                fieldWithPath("quantityOfHand").description("Quantity On Hand")
////                        )));
//   }
//
//    @Test
//    void saveNewBeer() throws Exception {
//
//        BeerDto beerDto = getValidBeerDto();
//        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
//
//        //ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
//
//        mockMvc.perform(post("/api/v1/beer/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(beerDtoJson))
//                .andExpect(status().isCreated());
////                .andDo(document("v1/beer-new",
////                        requestFields(
////                                fields.withPath("id").ignored(),
////                                fields.withPath("version").ignored(),
////                                fields.withPath("createdDate").ignored(),
////                                fields.withPath("lastModifiedDate").ignored(),
////                                fields.withPath("beerName").description("Name of the beer"),
////                                fields.withPath("beerStyle").description("Style of the beer"),
////                                fields.withPath("upc").description("Beer upc").attributes(),
////                                fields.withPath("price").description("Beer Price"),
////                                fields.withPath("quantityOfHand").ignored()
////                        )));
//    }
//
//    @Test
//    void updateBeerById() throws Exception {
//        BeerDto beerDto = getValidBeerDto();
//        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
//
//        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(beerDtoJson))
//                .andExpect(status().isNoContent());
//    }
//
//    BeerDto getValidBeerDto(){
//        return  BeerDto.builder()
//                .beerName("My beer")
//                .beerStyle(BeerStyleEnum.ALE)
//                .price(new BigDecimal("2.99"))
//                .upc(BeerLoader.BEER_1_UPC)
//                .build();
//    }
//
//    private static class ConstrainedFields {
//
//        private final ConstraintDescriptions constraintDescriptions;
//
//        ConstrainedFields(Class<?> input) {
//            this.constraintDescriptions = new ConstraintDescriptions(input);
//        }
//
//        private FieldDescriptor withPath(String path) {
//            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
//                    .collectionToDelimitedString(this.constraintDescriptions
//                            .descriptionsForProperty(path), ". ")));
//        }
//    }
//}
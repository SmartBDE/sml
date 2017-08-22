package principality.me.springtest.controller

import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody, RestController}
import principality.me.springtest.domain.model.Message
import principality.springtest.domain.model.Message
import yejf.springtest.domain.model.Message

/**
 * Created by win7 on 2017/8/2.
 */
@RestController
@RequestMapping(Array("/api"))
class ApiController {
  @RequestMapping(value = Array("/hello2"), method = Array(RequestMethod.GET))
  @ResponseBody
  def hello(): Message = {
    val message = new Message()
    message.setValue("Hello, Scala for Spring!")
    message
  }
}
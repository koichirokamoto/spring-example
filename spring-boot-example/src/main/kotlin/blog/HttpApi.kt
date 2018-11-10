package blog

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/article")
class ArticleController(private val repository: ArticleRepository, private val markdownConverter: MarkdownConverter) {

  @GetMapping("")
  fun findAll() = repository.findAllByOrderByAddedAtDesc()

  @GetMapping("/{id}")
  fun findOne(@PathVariable id: Long, @RequestParam converter: String?) = when (converter) {
    "markdown" -> repository.findById(id).map {
      it.copy(
        headline = markdownConverter.invoke(it.headline),
        content = markdownConverter.invoke(it.content)
      )
    }
    null -> repository.findById(id)
    else -> throw IllegalArgumentException("Only markdwon converter is supported")
  }
}

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

  @GetMapping("")
  fun findAll() = repository.findAll()

  @GetMapping("/{login}")
  fun findOne(@PathVariable login: String) = repository.findById(login)
}
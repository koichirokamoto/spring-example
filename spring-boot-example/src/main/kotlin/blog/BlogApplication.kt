package blog

import org.springframework.boot.Banner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
class BlogApplication {

  @Bean
  fun databaseInitializer(userRepository: UserRepository, articleRepository: ArticleRepository) = CommandLineRunner {
    val smaldini = User("smaldini", "Stéphane", "Maldini")
    userRepository.save(smaldini)
    articleRepository.save(
      Article(
        "Reactor Bismuth is out",
        "Lorem ipsum",
        "dolor **sit** amet https://projectreactor.io/",
        smaldini,
        1
      )
    )
    articleRepository.save(
      Article(
        "Reactor Aluminium has landed",
        "Lorem ipsum",
        "dolor **sit** amet https://projectreactor.io/",
        smaldini,
        2
      )
    )
  }
}

fun main(args: Array<String>) {
  runApplication<BlogApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}

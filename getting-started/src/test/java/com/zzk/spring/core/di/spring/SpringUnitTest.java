package com.zzk.spring.core.di.spring;

import com.zzk.spring.core.di.spring.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = SpringMainConfig.class)
//@ContextConfiguration(classes = { SpringMainConfig.class })
public class SpringUnitTest {
	@Autowired
	ApplicationContext context;

	@Test
	public void givenAccountServiceAutowiredToUserService_WhenGetAccountServiceInvoked_ThenReturnValueIsNotNull() {
		UserService userService = context.getBean(UserService.class);
		assertNotNull(userService.getAccountService());
	}

	@Test
	public void givenBookServiceIsRegisteredAsBeanInContext_WhenBookServiceIsRetrievedFromContext_ThenReturnValueIsNotNull() {
		BookService bookService = context.getBean(BookService.class);
		assertNotNull(bookService);
	}

	@Test
	public void givenBookServiceIsRegisteredAsBeanInContextByOverridingAudioBookService_WhenAudioBookServiceIsRetrievedFromContext_ThenNoSuchBeanDefinitionExceptionIsThrown() {
		BookService bookService = context.getBean(BookService.class);
		assertNotNull(bookService);
		AudioBookService audioBookService = context.getBean(AudioBookService.class);
		assertNotNull(audioBookService);
	}

	@Test
	public void givenAuthorServiceAutowiredToBookServiceAsOptionalDependency_WhenBookServiceIsRetrievedFromContext_ThenNoSuchBeanDefinitionExceptionIsNotThrown() {
		BookService bookService = context.getBean(BookService.class);
		assertNotNull(bookService);
	}

	@Test
	public void givenSpringPersonServiceConstructorAnnotatedByAutowired_WhenSpringPersonServiceIsRetrievedFromContext_ThenInstanceWillBeCreatedFromTheConstructor() {
		SpringPersonService personService = context.getBean(SpringPersonService.class);
		assertNotNull(personService);
	}

	@Test
	public void givenPersonDaoAutowiredToSpringPersonServiceBySetterInjection_WhenSpringPersonServiceRetrievedFromContext_ThenPersonDaoInitializedByTheSetter() {
		SpringPersonService personService = context.getBean(SpringPersonService.class);
		assertNotNull(personService);
		assertNotNull(personService.getPersonDao());
	}

}

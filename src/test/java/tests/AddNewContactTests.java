package tests;

import config.AppiumConfig;
import models.Auth;
import models.Contact;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactListScreen;

import java.util.Random;

public class AddNewContactTests extends AppiumConfig {

    @BeforeClass
    public void preCondition() {
        new AuthenticationScreen(driver)
                .fillLoginRegistrationForm(Auth.builder().email("mara@gmail.com")
                        .password("Mmar123456$").build())
                .submitLogin();
    }

    @Test
    public void createNewContactSuccess() {
        int i = new Random().nextInt(1000) + 1000;
        Contact contact = Contact.builder()
                .name("Simon")
                .lastName("Wow" + i)
                .email("wow" + i + "@gmail.com")
                .phone("36512363" + i)
                .address("Haifa")
                .description("Best friend")
                .build();

        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                   .isContactAddedByName(contact.getName(),contact.getLastName());
    }

    @Test
    public void createNewContactSuccessReq() {

    }

    @Test
    public void createContactWithEmptyName() {
        Contact contact = Contact.builder()
                .name("")
                .lastName("Wow")
                .email("wow@gmail.com")
                .phone("365123995663")
                .address("Haifa")
                .description("Empty name")
                .build();

        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactFormNegative()
                .isErrorContainsText("{name=must not be blank}");
    }



    @AfterClass
    public void postCondition() {
        new ContactListScreen(driver).logout();
    }
}

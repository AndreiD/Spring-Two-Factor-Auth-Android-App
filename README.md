# Two factor Auth Android App Template

Need more security ? Try Two Factor Auth

<img src="https://raw.githubusercontent.com/AndreiD/Spring-Two-Factor-Auth-Android-App/master/device-2016-12-05-162620.png" width="300px" alt="two factor auth android sample">

### What is this ?
A sample application that can be used as a template for implementing Mobile Two Factor Auth 

The major drawback of authentication performed including something that the user possesses is that the physical token (the USB stick, the bank card, the key or similar) must be carried around by the user, practically at all times. Loss and theft are a risk. There are also costs involved in procuring and subsequently replacing tokens of this kind. In addition, there are inherent conflicts and unavoidable trade-offs between usability and security.

### Mobile phone two-factor authentication

Devices such as mobile phones and smartphones serve as "something that the user possesses", was developed to provide an alternative method that would avoid such issues. To authenticate themselves, people can use their personal access license (i.e. something that only the individual user knows) plus a one-time-valid, dynamic passcode consisting of digits. The code can be sent to their mobile device by SMS or via a special app. The advantage of this method is that there is no need for an additional, dedicated token, as users tend to carry their mobile devices around at all times anyway.

### Advantages of mobile phone two-factor authentication

*User Friendly*
No additional tokens are necessary because it uses mobile devices that are (usually) carried all the time.
As they are constantly changed, dynamically generated passcodes are safer to use than fixed (static) log-in information.
Depending on the solution, passcodes that have been used are automatically replaced in order to ensure that a valid code is always available; acute transmission/reception problems do not therefore prevent logins.
The option to specify a maximum permitted number of incorrect entries reduces the risk of attacks by unauthorized persons.
It is easy to configure; user friendly.

#### Disadvantages of mobile phone two-factor authentication

The mobile phone must be carried by the user, charged, and kept in range of a cellular network whenever authentication might be necessary. If the phone is unable to display messages, access is often impossible without backup plans.

Mobile phones can be stolen, potentially allowing the thief to gain access into the user's accounts

### How it works ?

A code is generated and showed on the screen after a user registers an account. The code is copied on the phone and will act as part of the encryption key.
The google push notification token is send to the server.
Everytime a user will try to login, they will get a push notification and they have to click the Yes button on their phone.

<img src="https://raw.githubusercontent.com/AndreiD/Spring-Two-Factor-Auth-Android-App/master/device-2016-12-05-162541.png" width="500px" alt="two factor auth android">

# Android CTF Challenge App

Welcome to the Android CTF Challenge App! This project contains several challenges designed to test your skills in static and dynamic analysis of Android applications. The challenges include finding hashed passwords, interpreting code to determine the correct input, intercepting server-client communication, and discovering secret codes.

## Table of Contents
1. [Warm-up: Strings grep FLAG](#warm-up-strings-grep-flag)
2. [Login Page Hashed Password](#login-page-hashed-password)
3. [Make Your Own Present Time](#make-your-own-present-time)
4. [Server-Client Intercept](#server-client-intercept)
5. [The Secret Code of the Bank](#the-secret-code-of-the-bank)

## Warm-up: Strings grep FLAG
In this challenge, you'll start by searching through the APK file for hidden flags using the `strings` command. This will help you get familiar with the app's structure and hidden elements.

### Steps
1. Download the APK.
2. Use the `strings` command to find potential flags.
3. Locate the flag with the format `FLAG{...}`.

## Login Page Hashed Password
This challenge involves finding a hashed password using static analysis. The password is hashed using MD5 and stored in the app.

![Login Page Screenshot](Screenshots/Login_Page.png)

### Steps
1. Decompile the APK using JADX.
2. Locate the login functionality in the code.
3. Find the MD5 hashed password and use an MD5 decryption site to find the plaintext password.
4. Login using the decrypted password to capture the flag.
5. Good job! We are in!

## Make Your Own Present Time
In this challenge, you need to provide the correct time based on your analysis of the code.

![Present Time Screenshot](Screenshots/Time_Page.png)

### Steps
1. Decompile the APK using JADX.
2. Analyze the code to determine the correct time format.
3. Input the correct time to capture the flag.
Hint: Can you tell the time for me, please?

## Server-Client Intercept
This dynamic analysis challenge requires intercepting the communication between the server and client using Wireshark. The input words are randomized.

![Server-Client Intercept Screenshot](Screenshots/Apple_Page.png)

### Steps
1. Set up Wireshark to capture network traffic from the app. In case the IP is not correct, you can set the ip in the code to your phone ip. Still work in progress sorry :(.
2. Interact with the app to generate server-client communication.
3. Analyze the intercepted traffic to find the randomized input words.
4. Use the correct input words to capture the flag.
Hint: The input is randomized every time fyi

## The Secret Code of the Bank
In this challenge, you'll need to find the secret code of the bank hidden within the app's code.

![Secret Code Screenshot](Screenshots/Card_Page.png)

### Steps
1. Decompile the APK using JADX or use Frida and use the javascript file from scripts file. Your
2. Navigate through the code to find references to the secret code.
3. Identify the correct secret code and use it to capture the flag.
4. The flag is the "stolen" credit card. Good job! 
5. You can have your payday! :)


## Write-Ups
Provide brief descriptions and steps for each challenge here. Detailed write-ups can be included in separate markdown files or linked from this section.

![Login Page Hashed Password Write-Up](Screenshots/Login_decomp.png)
![Make Your Own Present Time Write-Up](Screenshots/Time.png)
![Server-Client Intercept Write-Up](Screenshots/Wireshark.png)
## ![The Secret Code of the Bank Write-Up](path/to/secret_code_writeup.md)

## Contributing
Authors are Cezar-Marian Sava and Mihai-Florian Miu.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

Happy Hacking!

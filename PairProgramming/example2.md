# Example1
Create **App** by using the following configuration and design

## Name
Hello1

### Configuration
- os
    - android
    - iOS
- device orientation
    - all
- compiled SDK
    - default 
- min device compatibility 
    - default 

#### Design
- Scene
    - Label
        - title: "Hello World"
        - color: red

---

# Example2
Create **App** by using the following configuration, design and logic

## Name
Hello2

### Configuration
- Android
- iOS

#### Design
- Scene
    - Label
        - name: myLabel
        - title: "Hello World"
        - color: red
    - Button
        - name: myButton
        - title: "Tap here"

##### Logic
- myButton
    - tap action
        - myLabel
            - title: "Button Tapped"

---

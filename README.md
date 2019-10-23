# Cordova-Blink-Card-Reader (Android ONLY)

## Example
<img src="https://github.com/hitmacreed/Cordova-Blink-Card-Reader/blob/master/art/blink-card-fast.gif" width="400">

## Installation

```sh
 cordova plugin add https://github.com/hitmacreed/Cordova-Blink-Card-Reader.git 
```

```sh
 ionic cordova plugin https://github.com/hitmacreed/Cordova-Blink-Card-Reader.git
```

## Info
Blink SDK Plugin for scanning and OCR of credit or payment cards(Debit Cards).
Get your lincense on [BLINK WEBSITE](https://microblink.com) and go developer dashboard to get your Base64 lincense
[Android Official SDK](https://github.com/blinkcard/blinkcard-android)
[Ionic officail Plugin](https://ionicframework.com/docs/native/blinkid)

<img src="https://github.com/hitmacreed/Cordova-Blink-Card-Reader/blob/master/art/pic1.png?raw=true" width="800">

## Usage Ionic >= 3 and cordova

 Declare in your component in ionic 3 or 4, cordova not needed
```javascript
 declare var BlinkCardReader: any;
```

 Usage in ionic >= 3
```javascript

    BlinkCardReader.read(
      
      function (objResult) {
        console.log(objResult);
         /*
         objResult = [
          cardNumber:'xxxx xxxx xxxx xxxx',   
         cardgetOwner:'USERNAME', 
         cardExpDate:'12-12-2019',
         cardCvv: 'xxx' //OPTIONAL RESULT 
         }
         */
      },

      function (error) {
          console.log(error);
      },
      {
        Cvv : false, // true || false
    	androidKey : "xxxxxxxxxxxx..." // Base64 license, get on Blink Developer DashBoard
      }
   );
```

 Usage in cordova
```javascript
    cordova.plugins.BlinkCardReader.read(
      
      function (objResult) {
        console.log(objResult)
         /*
         objResult = [
          cardNumber:'xxxx xxxx xxxx xxxx',   
         cardgetOwner:'USERNAME', 
         cardExpDate:'12-12-2019',
         cardCvv: 'xxx' //OPTIONAL RESULT 
         }
         */
      },

      function (error) {
          console.log(error);
      },
      {
        Cvv : false, // true || false
    	androidKey : "xxxxxxxxxxxx..." // Base64 license, get on Blink Developer DashBoard
      }
   );
```

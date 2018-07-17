# ShutterEndlessScrollView ducument
#### 1. Project problems and solution
1. Shutter Stock Image authentication process
The authentication has two ways:
1) Http Basic way: use clientId and clientSecretary to be added in the request header
2) Oath 2.0:redirect to login page and after login to get the code to fetch token. use the token to get the images.

I chose the second way first, but it needs to redirect to login page and input the account and password, this has much more complex logic and has security problem, using okhttp to add the clientId and clientSecretary to the header is much more convenient.

2. databing issue
When layout xml file contains error, the databinding class will not be generated, related method cannot be used.

3. image out of menmory issue
call fit method of picasso to resize the image, this will reduce the memory cost and use defaut image in the resource file when the image has not been loaded successfully. Besides, set no request when scrolling will help solve the OOM issue.

4. parameters using basic type will be reloaded without observable
parameters like showProgress, showRecyclerview using basic boolean will not change status unless using ObserverableBoolean, because data binding way need observerable variables.

5. scroll end detecting condition
using item and footer to distinguish and decide to weather to load more.

#### 2. technical choices and architectural
The project uses Android, RxAndroid, Retrofit, OkHttp, Dagger2, Rcyclerview, picasso
Rxandroid is easy to process steps sequence and the structure is clear;
Retrofit and Okhttp are the latest http framework;
Dagger uses dependency injection and easy to maintain instances objects;
picasso can provide images with best perfomance.
the architectural pattern uses MVVM, which uses data binding to easy bind the view and logic process.

#### 3. trade-offs
I give up login process and use http basic request to search the images, this contains security issues.

#### 4. Link to other code you're particularly proud of
please refer to other repositories in my github.

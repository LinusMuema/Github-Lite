import 'package:flutter/material.dart';



void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'GitHub Lite',
       routes: {
    
    '/': (context) => OnboadingPage(),
    // When navigating to the "/second" route, build the SecondScreen widget.
    '/loginPage': (context) => LoginPage(),
  },
    );
  }
}

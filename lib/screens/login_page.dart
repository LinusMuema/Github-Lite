import 'package:flutter/material.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  @override
  Widget build(BuildContext context) {
     return SafeArea(
          child: Scaffold(
        body: Container(
          color: Colors.black,
          child: Padding(
            padding: EdgeInsets.all(20),
            child: Column(
              children: <Widget>[
                SizedBox(
                  height: 50,
                ),
                Container(
                  height: 80,
                  width: 80,
                  child: CircleAvatar(
                      backgroundColor: Colors.white,
                      backgroundImage: AssetImage('assets/octohub.png')),
                ),
                Text(
                  "Gitlite",
                  style: TextStyle(
                      color: Colors.white,
                      fontSize: 40,
                      fontWeight: FontWeight.bold),
                ),
                SizedBox(
                  height: 50,
                ),
               SizedBox(height: 30,),
                MaterialButton(
                  padding: EdgeInsets.all(20),
                  color: Colors.blue,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10),
                  ),
                  child: Align(
                    child: 
                    
                    //Provider.of<UserProvider>(context).isLoading() ? 
                    CircularProgressIndicator(backgroundColor: Colors.white, strokeWidth: 2,) :
                    Text(
                      'Get started on Gitlite',
                      style: TextStyle(color: Colors.white),
                    ),
                  ),
                  onPressed: () {
                   
                  },
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
  }

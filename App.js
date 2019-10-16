/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import {
  Platform, StyleSheet, Text, View, KeyboardAvoidingView,
  TouchableOpacity, Keyboard, TextInput, NativeModules, NativeEventEmitter,
  SafeAreaView
} from 'react-native';
import CustomTextEditor from './CustomTextEditor';
import { DeviceEventEmitter } from 'react-native';

import CustomTextEditorAndroid from './CustomTextEditorAndroid';
// const {RNTRichTextEditor} = NativeModules; 
const { Helper } = NativeModules;

export default class App extends Component {
  constructor(props){
    super(props);
    this.state={
      textString : ''
    }
  }
  componentDidMount() {
   
 if (Platform.OS == 'android') {

      DeviceEventEmitter.addListener('clickEventName', (data) => {
          //console.log("got the touch event", data)
        
      })
   
  }

}

  render() {
    return (
      <SafeAreaView style={styles.container}>
        <KeyboardAvoidingView behavior="padding" >
          <TouchableOpacity onPress={this.customKeyboard} style={{ color: 'black' }}>
            <Text>
              {this.state.textString}
            </Text>

          </TouchableOpacity>
        
          <CustomTextEditor style={{ height: 400, width: 600 }} />
        </KeyboardAvoidingView>
      </SafeAreaView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
    // backgroundColor: 'black',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

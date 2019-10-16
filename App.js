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
import Dimensions from 'Dimensions';

import CustomTextEditorAndroid from './CustomTextEditorAndroid';
// const {RNTRichTextEditor} = NativeModules; 
const { Helper } = NativeModules;
const { width, height } = Dimensions.get('window');
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
        <View style={{ flex:1,backgroundColor:'blue' }}>
        <KeyboardAvoidingView behavior="padding" >
          <TouchableOpacity onPress={this.customKeyboard} style={{ color: 'black' }}>
            <Text>
              {this.state.textString}
            </Text>

          </TouchableOpacity>
        
          <CustomTextEditor style={{ height: 150, width: width }} />
        </KeyboardAvoidingView>
        </View>
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

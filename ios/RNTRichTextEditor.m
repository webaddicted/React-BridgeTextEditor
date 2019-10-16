//
//  RNTRichTextEditor.m
//  BridgeTest
//
//  Created by Rohit Advani  on 8/1/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "RichTextEditor.h"
#import <React/RCTViewManager.h>
#import <React/RCTUIManager.h>
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#import "AppDelegate.h"


/**********/
@interface Helper : RCTEventEmitter<RCTBridgeModule>
@end

@implementation Helper

RCT_EXPORT_MODULE(Helper)

- (NSArray<NSString *> *)supportedEvents
{
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(callhelpermethod:)
                                               name:@"TestNotification"
                                             object:nil];
  return @[@"TextFieldDismissed"];
}

-(void)callhelpermethod:(NSNotification *) notification {
  AppDelegate *appDelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
  [self sendEventWithName:@"TextFieldDismissed" body:appDelegate.strValue];
}

RCT_EXPORT_METHOD(goAway: (RCTResponseSenderBlock)callback) {  
  //[self callhelpermethod];
  callback(@[@"first"]);
}




@end

/***************/

@interface RNTRichTextEditorManager : RCTViewManager
@end

@implementation RNTRichTextEditorManager

RCT_EXPORT_MODULE(RNTRichTextEditor)

- (RichTextEditor *)view
{
  return [[RichTextEditor alloc] init];
}



RCT_EXPORT_METHOD(htmlString: (RCTResponseSenderBlock)callback) {
//  Helper *helperObject = [[Helper alloc] init];
//  [helperObject callhelpermethod];
  //[self sendEventWithName:@"TextFieldDismissed" body:@"Second"];
  callback(@[@"first"]);
}

@end


import { requireNativeComponent, Platform } from 'react-native';

if(Platform.OS=='ios'){
    module.exports = requireNativeComponent('RNTRichTextEditor');
}
else {
    module.exports = requireNativeComponent('MaterialCalendarView');
}


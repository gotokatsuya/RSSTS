// IIntentReceiveService.aidl
package com.goka.rssts;

import com.goka.rssts.IIntentReceiveServiceCallback;

interface IIntentReceiveService {
    oneway void registerCallback(IIntentReceiveServiceCallback callback);
    oneway void unregisterCallback(IIntentReceiveServiceCallback callback);
}
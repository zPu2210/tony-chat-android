#!/bin/bash

# Fix script for compile errors
set -e

cd "/Users/pu/Documents/Playground/Tony Chat/android"

echo "Fixing compile errors..."

# ChatActivity - fix UnsupportedFileType string references
sed -i '' 's/LocaleController.getString("UnsupportedFileType", R.string.UnsupportedFileType)/LocaleController.getString(R.string.UnsupportedAttachment)/g' TMessagesProj/src/main/java/org/telegram/ui/ChatActivity.java

# ChatRightsEditActivity - remove dead code block with HapticFeedbackConstants
sed -i '' '1377,1381d' TMessagesProj/src/main/java/org/telegram/ui/ChatRightsEditActivity.java

# MessagesController - add Toast import
sed -i '' '/^import android.content.Context;/a\
import android.widget.Toast;\
' TMessagesProj/src/main/java/org/telegram/messenger/MessagesController.java

# DownloadController - add TextUtils import
sed -i '' '/^import android.os.SystemClock;/a\
import android.text.TextUtils;\
' TMessagesProj/src/main/java/org/telegram/messenger/DownloadController.java

# FilteredSearchView - add Toast import
sed -i '' '/^import android.widget.TextView;/a\
import android.widget.Toast;\
' TMessagesProj/src/main/java/org/telegram/ui/FilteredSearchView.java

echo "Done!"

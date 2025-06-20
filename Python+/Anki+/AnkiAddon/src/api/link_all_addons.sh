set -e
anki_addons_dir=/home/aleks/.local/share/Anki2/addons21
addons_api_dir=/home/aleks/pr/home/yaal_examples/Python+/Anki+/AnkiAddon/src/api
src_relative_paths=(
  AddonConfig+/OpenAddonConfigDialog
  AddonConfig+/ReadWriteAddonConfig
  AddonInfo
  Collection
  Cython+/CythonAddon/src
  Dependencies+/addon_with_dependency
  Dependencies+/addon_with_dependency_pip
  GUI+/QProgressDialog
  GUI+/QtAPI+/NoteSizeConfig
  GUI+/QtAPI+/QDialogButtonBox
  Hook+/GenHooks+/FieldFilter
  Hook+/GenHooks+/NotesWereUpdated
  Hook+/GenHooks+/NotesWillBeDeleted
  Hook+/GenHooks+/NoteWillBeAdded
  Hook+/GenHooks+/NoteWillFlush
  Hook+/GuiHooks+/AddCards+/AddCardsDidAddNote
  Hook+/GuiHooks+/Browser+/BrowserDidFetchColumns
  Hook+/GuiHooks+/Browser+/BrowserDidFetchRow
  Hook+/GuiHooks+/Browser+/BrowserDidSearch
  Hook+/GuiHooks+/Browser+/BrowserWillSearch
  Hook+/GuiHooks+/Browser+/BrowserWillShow
  Hook+/GuiHooks+/Browser+/BrowserWillShowContextMenu
  Hook+/GuiHooks+/Collection+/CollectionDidLoad
  Hook+/GuiHooks+/DeckBrowser+/DeckBrowserDidRender
  Hook+/GuiHooks+/DeckBrowser+/DeckBrowserWillRenderContent
  Hook+/GuiHooks+/Editor+/EditorDidFireTypingTimer
  Hook+/GuiHooks+/Editor+/EditorDidInit
  Hook+/GuiHooks+/Editor+/EditorDidInitButtons
  Hook+/GuiHooks+/Editor+/EditorDidInitShortcuts
  Hook+/GuiHooks+/Editor+/EditorDidLoadNote
  Hook+/GuiHooks+/Editor+/EditorDidPaste
  Hook+/GuiHooks+/Editor+/EditorDidUnfocusField
  Hook+/GuiHooks+/Editor+/EditorStateDidChange
  Hook+/GuiHooks+/Editor+/EditorWebViewDidInit
  Hook+/GuiHooks+/Focus+/FocusDidChange
  Hook+/GuiHooks+/MainWindow+/MainWindowDidInit
  Hook+/GuiHooks+/Profile+/ProfileDidOpen
  Hook+/GuiHooks+/Profile+/ProfileWillClose
  Hook+/GuiHooks+/Reviewer+/ReviewerDidInit
  Hook+/GuiHooks+/Reviewer+/ReviewerDidShowQuestion
  Hook+/GuiHooks+/Reviewer+/ReviewerWillShowContextMenu
  Hook+/GuiHooks+/Stats+/StatsDialogWillShow
  Hook+/GuiHooks+/Sync+/MediaSyncDidProgress
  Hook+/GuiHooks+/Sync+/MediaSyncDidStartOrStop
  Hook+/GuiHooks+/Sync+/SyncDidFinish
  Hook+/GuiHooks+/Sync+/SyncWillStart
  Hook+/GuiHooks+/Theme+/ThemeDidChange
  Hook+/GuiHooks+/WebView+/WebviewDidInjectStyleIntoPage
  Hook+/GuiHooks+/WebView+/WebViewDidReceiveJsMessage
  Hook+/GuiHooks+/WebView+/WebviewWillSetContent
  Logging+/AddonManager
  Logging+/log_to_file_basic
  Logging+/log_to_file_handler
  Logging+/log_to_stderr
  Media/OpenCheckMediaDialog
  SortByCustomColumn
  Scenarios+/BackgroundOperations+/CollectionOp
  Scenarios+/BackgroundOperations+/ProgressManager+/ProgressManager
  Scenarios+/BackgroundOperations+/QueryOp+/RunInBackground+/FailureCallback
  Scenarios+/BackgroundOperations+/QueryOp+/RunInBackground+/FailurePopup
  Scenarios+/BackgroundOperations+/QueryOp+/RunInBackground+/NoProgressInherit
  Scenarios+/BackgroundOperations+/QueryOp+/RunInBackground+/NoProgress
  Scenarios+/BackgroundOperations+/QueryOp+/RunInBackground+/WithoutCollection
  Scenarios+/BackgroundOperations+/QueryOp+/RunInBackground+/WithProgressConcurrent
  Scenarios+/BackgroundOperations+/QueryOp+/RunInBackground+/WithProgressCounter
  Scenarios+/BackgroundOperations+/QueryOp+/RunInBackground+/WithProgressCounterInherit
  Scenarios+/BackgroundOperations+/QueryOp+/RunInBackground+/WithProgressNoCounter
  Scenarios+/Browser/RefreshCurrentNote
  Scenarios+/Browser/ShowHideColumn
  Scenarios+/BrowserDoSearch
  Scenarios+/BrowserSelectedNotes
  Scenarios+/BrowserUpdate
  Scenarios+/DeckBrowserRefresh
  Scenarios+/DialogManager
  Scenarios+/MainWindowMenuItem
  Scenarios+/SwitchProfile
  Utils/ShowException
  Utils/ShowInfo
)
for src_relative_path in "${src_relative_paths[@]}"; do
  src_full_path=${addons_api_dir}/${src_relative_path}
  safe_name=${src_relative_path//\//-}
  safe_name=${safe_name//+/}
  dest_full_path=${anki_addons_dir}/${safe_name}
  if [ -L $dest_full_path ]; then
    echo "Unlinking $dest_full_path"
    unlink $dest_full_path
  fi
  echo "Linking   $src_full_path to $dest_full_path"
  ln -s $src_full_path $dest_full_path

  dest_common_dir=${src_full_path}/_common
  src_common_dir=${addons_api_dir}/common
  if [ -L $dest_common_dir ]; then
    echo "Unlinking $dest_common_dir"
    unlink $dest_common_dir
  fi
  echo "Linking   $src_common_dir to $dest_common_dir"
  ln -s $src_common_dir $dest_common_dir
done

set -e
anki_addons_dir=/home/aleks/.local/share/Anki2/addons21
addons_api_dir=/home/aleks/pr/home/yaal_examples/Python+/Anki+/Addon+/API+
src_relative_paths=(
  AddonConfig
  AddonInfo
  Collection
  Dependencies+/addon_with_dependency
  Dependencies+/addon_with_dependency_pip
  Hook+/GUI+/Browser+/BrowserDidFetchColumns
  Hook+/GUI+/Browser+/BrowserDidFetchRow
  Hook+/GUI+/Browser+/BrowserDidSearch
  Hook+/GUI+/Browser+/BrowserWillSearch
  Hook+/GUI+/Browser+/BrowserWillShow
  Hook+/GUI+/Browser+/BrowserWillShowContextMenu
  Hook+/GUI+/Collection+/CollectionDidLoad
  Hook+/GUI+/DeckBrowser+/DeckBrowserDidRender
  Hook+/GUI+/DeckBrowser+/DeckBrowserWillRenderContent
  Hook+/GUI+/Editor+/EditorDidFireTypingTimer
  Hook+/GUI+/Editor+/EditorDidInit
  Hook+/GUI+/Editor+/EditorDidInitButtons
  Hook+/GUI+/Editor+/EditorDidLoadNote
  Hook+/GUI+/Editor+/EditorDidPaste
  Hook+/GUI+/Editor+/EditorDidUnfocusField
  Hook+/GUI+/Editor+/EditorStateDidChange
  Hook+/GUI+/Editor+/EditorWebViewDidInit
  Hook+/GUI+/MainWindow+/MainWindowDidInit
  Hook+/GUI+/Profile+/ProfileDidOpen
  Hook+/GUI+/Stats+/StatsDialogWillShow
  Hook+/GUI+/WebView+/WebViewDidReceiveJsMessage
  Hook+/GUI+/WebView+/WebviewWillSetContent
  Hook+/PyLib+/field_filter
  Logging+/AddonManager
  Logging+/log_to_file_basic
  Logging+/log_to_file_handler
  Logging+/log_to_stderr
  Media
  SortByCustomColumn
  UI+/BackgroundOperations+/CollectionOp
  UI+/BackgroundOperations+/QueryOp
  UI+/BrowserDoSearch
  UI+/BrowserSelectedNotes
  UI+/MainWindowMenuItem
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

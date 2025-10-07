# VRCVideoLogViewer
## なにこれ
VRChatのログから動画や画像、テキストの取得のURLを抜き出すツールです。
## 使い方 (詳しくない人向け)
- [Releases](https://github.com/nicovrc-net/VRCVideoLogViewer/releases)から一番上のAssetsをクリック
- VRCVideoLogViewer.zipをダウンロード
- ダウンロードしたVRCVideoLogViewer.zipを展開
- 出てきたstartファイルをダブルクリックで起動
- 出てきた画面の通りに従う

## 使い方 (詳しい人向け)
- [Releases](https://github.com/nicovrc-net/VRCVideoLogViewer/releases)から一番上のAssetsをクリック
- VRCVideoLogViewer.zipをダウンロード
- ダウンロードしたVRCVideoLogViewer.zipを展開
- コマンドプロンプトなどを開いて出来上がったフォルダに移動する
- ``java -jar ./VRCVideoLogViewer-1.0-SNAPSHOT-all.jar``
- 出てきた画面の通りに従う

## 使い方 (ビルドとか自力でできる人向け,Windowsの場合)
- ``git clone https://github.com/nicovrc-net/VRCVideoLogViewer.git``
- ``git checkout release``
- ``./gradlew.bat shadowJar``
- ``cd ./build/lib``
- ``java -jar ./VRCVideoLogViewer-1.0-SNAPSHOT-all.jar``
- 出てきた画面の通りに従う

## 使い方 (ビルドとか自力でできる人向け,Linuxの場合)
- ``git clone https://github.com/nicovrc-net/VRCVideoLogViewer.git``
- ``git checkout release``
- ``./gradlew shadowJar``
- ``cd ./build/lib``
- ``java -jar ./VRCVideoLogViewer-1.0-SNAPSHOT-all.jar``
- 出てきた画面の通りに従う
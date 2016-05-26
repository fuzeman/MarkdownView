# MarkdownView

[![][badge-bintray]][badge-bintray-href] [![][badge-license]](LICENSE.md)

MarkdownView provides a simple method of displaying Markdown in Android applications, with support for:

 - Markdown flavours (Basic, Extended, Github)
 - Custom markdown blocks, tokens and decorators
 - Themes

This is a fork of the original [MarkdownView](https://github.com/falnatsheh/MarkdownView) project, but uses [markdown-java](https://github.com/fuzeman/markdown-java) for parsing.

## Download

**Gradle**
```gradle
repositories {
    maven { url "http://dl.bintray.com/fuzeman/maven" }
}

dependencies { 
    compile 'net.dgardiner.mdv:markdownview:1.0.1'
}
```

## Usage

Add MarkdownView to your layout:

```xml
<net.dgardiner.mdv.MarkdownView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/mdv" />
```

and reference it in your Activity/Fragment:

```java
MarkdownView mdv = (MarkdownView) findViewById(R.id.mdv);
mdv.loadMarkdown("## Hello Markdown"); 
```

## Themes

Themes can be used with:
```java
mdv.loadMarkdown(<markdown>, "file:///android_asset/MyCustomTheme.css");
```

Themes bundled with the library can be found [here](https://github.com/fuzeman/MarkdownView/tree/master/app/src/main/assets/markdown_css_themes).

## API

### `MarkdownView`

#### `loadMarkdown`

```java
loadMarkdown(String markdown)
loadMarkdown(String markdown, String themeUrl)
```

Loads the given markdown.

#### `loadMarkdownAsset`

```java
loadMarkdownAsset(String assetUri)
loadMarkdownAsset(String assetUri, String themeUrl)
```

Loads the given markdown asset.

Application assets can be referenced with:
```java
file:///android_asset/[filename]
```

#### `loadMarkdownUrl`
 
```java
loadMarkdownUrl(String url)
loadMarkdownUrl(String url, String themeUrl)
```

Loads the given markdown URL.

#### `setPageStartedListener`

```java
setPageStartedListener(OnPageStartedListener listener)
```

#### `setPageFetchedListener`

```java
setPageFetchedListener(OnPageFetchedListener listener)
```

#### `setPageParsedListener`

```java
setPageParsedListener(OnPageParsedListener listener)
```

#### `setPageLoadingListener`

```java
setPageLoadingListener(OnPageLoadingListener listener)
```

#### `setPageFinishedListener`

```java
setPageFinishedListener(OnPageFinishedListener listener)
```


[badge-bintray]: https://img.shields.io/bintray/v/fuzeman/maven/MarkdownView.svg?maxAge=2592000
[badge-bintray-href]: https://bintray.com/fuzeman/maven/MarkdownView
[badge-license]: https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg?style=flat-square
[ ![Download](https://api.bintray.com/packages/lankton/maven/flowlayout/images/download.svg) ](https://bintray.com/lankton/maven/flowlayout/_latestVersion)

# android-flowlayout
A very convenient and powerful flow layout created by Lan.

## minSdkVersion
11

## Dependencies
### latest version
see it as an icon in the beginning of the README file.
### gradle
```
compile 'cn.lankton:flowlayout:latest version'
```
### maven
```
<dependency>
  <groupId>cn.lankton</groupId>
  <artifactId>flowlayout</artifactId>
  <version>latest version</version>
  <type>pom</type>
</dependency>
```
## Effect
e
<img src="https://github.com/lankton/android-flowlayout/blob/master/pictures/flowlayout_add.gif?raw=true" width="260px"/>
<img src="https://github.com/lankton/android-flowlayout/blob/master/pictures/flowlayout_compress.gif?raw=true" width="260px"/>
<img src="https://github.com/lankton/android-flowlayout/blob/master/pictures/flowlayout_align.gif?raw=true" width="260px"/>   
**pic left**:  **add** views into the flowlayout constantly  
**pic middle**: **compress** child elements to make them spend lines as fiew as possible and look tight   
**pic right**: **align** child elements to make them look in alignment.  

<img src="https://github.com/lankton/android-flowlayout/blob/master/pictures/flowlayout_specify.gif?raw=true" width="260px"/> 

This pic shows to cut the flowlayout to the specified number of lines.
## Usage
### normal usage like other flow layouts
You can use this FlowLayout like below in your layout file
```xml
<cn.lankton.flowlayout.FlowLayout
        android:id="@+id/flowlayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="10dp"
        app:lineSpacing="10dp"
        android:background="#F0F0F0">

</cn.lankton.flowlayout.FlowLayout>
```
As you see, this FlowLayout provides an attribute named "**lineSpacing**", which can be used to set the distance between lines in the FlowLayout.
### compress child elements
When you want to compress child elements, use the code like below:  
```
flowLayout.relayoutToCompress();
```
The FlowLayout compresses child elements **by changing their sequence**.
### align child elemnts
When you want to align child elements, use the code like below: 
```
flowLayout.relayoutToAlign();
```
The order of child elements **won't be changed**.
### cut
You can cut the flowlayout to the specified number of lines.
```
flowLayout.specifyLines(int)
```
## summary
May this FlowLayout help you. Thanks.

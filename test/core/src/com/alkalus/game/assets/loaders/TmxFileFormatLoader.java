package com.alkalus.game.assets.loaders;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.magnos.asset.AssetInfo;
import org.magnos.asset.base.BaseAssetFormat;
import org.magnos.asset.xml.XmlInfo;
import org.w3c.dom.Document;

public class TmxFileFormatLoader extends BaseAssetFormat {
	public TmxFileFormatLoader() {
		super(new String[]{"tmx"}, new Class[]{Document.class});
	}

	public AssetInfo getInfo(Class<?> type) {
		return new XmlInfo(type);
	}

	public Document loadAsset(InputStream input, AssetInfo assetInfo) throws Exception {
		XmlInfo info = (XmlInfo) assetInfo;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(info.isValidating());
		factory.setIgnoringComments(info.isIgnoreComments());
		factory.setIgnoringElementContentWhitespace(info.isIgnoreWhitespace());
		factory.setCoalescing(info.isCoalescing());
		factory.setNamespaceAware(info.isNamespaceAware());
		factory.setExpandEntityReferences(info.isExpandReferences());
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(input);
	}
}
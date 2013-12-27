/**
 * Code heavily influenced by Kiran Pai, 2002: 
 * http://www.developerfusion.com/code/2064/a-simple-way-to-read-an-xml-file-in-java/
 */

package com.donkeyenough.loader;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Loader {

	public static Map<String, String> map = new HashMap<String, String>();
	
	public static String DICTIONARY_FILES_FOLDER = "gcide_xml/xml_files/";
	
	public static String[] files = {
			/*"xgcide_a.xml",
			"xgcide_b.xml", 
			"xgcide_c.xml",
			"xgcide_d.xml" , 
			*/"xgcide_e.xml",
			"xgcide_f.xml",
			"xgcide_g.xml",
			"xgcide_h.xml",
			"xgcide_i.xml",
			"xgcide_j.xml",
			"xgcide_k.xml",
			"xgcide_l.xml",
			"xgcide_m.xml",
			"xgcide_n.xml",
			"xgcide_o.xml",
			"xgcide_p.xml",
			"xgcide_q.xml",
			"xgcide_r.xml",
			"xgcide_s.xml",
			"xgcide_t.xml",
			"xgcide_u.xml",
			"xgcide_v.xml",
			"xgcide_w.xml",
			"xgcide_x.xml",
			"xgcide_y.xml",
			"xgcide_z.xml"
//			"gcide_abbreviations.xml",
//			"gcide_authorities.xml"			*/
	};
	
	public static String[] unconvertedFiles = {
		"gcide_a.xml",
		"gcide_b.xml",
		"gcide_c.xml",
		"gcide_d.xml",
		"gcide_e.xml",
		"gcide_f.xml",
		"gcide_g.xml",
		"gcide_h.xml",
		"gcide_i.xml",
		"gcide_j.xml",
		"gcide_k.xml",
		"gcide_l.xml",
		"gcide_m.xml",
		"gcide_n.xml",
		"gcide_o.xml",
		"gcide_p.xml",
		"gcide_q.xml",
		"gcide_r.xml",
		"gcide_s.xml",
		"gcide_t.xml",
		"gcide_u.xml",
		"gcide_v.xml",
		"gcide_w.xml",
		"gcide_x.xml",
		"gcide_y.xml",
		"gcide_z.xml"
//		"gcide_abbreviations.xml",
//		"gcide_authorities.xml"			
};
	
	public static String[] tagNames = {
	
		"CAPTION",
		"H1",//        (#PCDATA)>
		"H2",//        (#PCDATA)>
		"I",//         (#PCDATA)>
		"TITLE",//     (#PCDATA)>
		"a",//         (#PCDATA)>
		"ab",//        (#PCDATA)>
		"ab.entry",//  (ab|ab.full)*>
		"ab.full",//   (#PCDATA|it)*>
		"abbr",//      (#PCDATA|it|sc)*>
		"adjf",//      (#PCDATA)>
		"altname",//   (#PCDATA|asp|class|col|colf|cref|ecol|er|fam|gen|hw|ord|spn|sub|subclass|sup)*>
		"altnpluf",//  (#PCDATA)>
		"altsp",//     (#PCDATA|abbr|as|asp|au|er|ets|ex|grk|hw|it|pluf|plw|pos|pr|sd|wf)*>
		"amorph",//    (#PCDATA|adjf|au|er|ex|it|pos|pr|qex|xex)*>
		"ant",//       (#PCDATA)>
		"antiquetype",// (#PCDATA)>
		"as",//        (#PCDATA|altname|ant|au|chform|city|col|contr|corpn|cref|ecol|er|ex|examp|exp|fam|film|fld|frac|fract|gen|grk|hascons|it|mark|mathex|mcol|ord|org|partof|persfn|person|pos|ptcl|qex|sc|sd|sig|spn|styp|stype|subs|universbold|xex)*>
		"asp",//       (#PCDATA|fam|gen|hw)*>
		"au",//        (#PCDATA|i|mark)*>
		"au.entry",//  (au|au.see|au.who|au.work)*>
		"au.see",//    (#PCDATA|i)*>
		"au.who",//    (#PCDATA|i)*>
		"au.work",//   (#PCDATA|i)*>
		"b",//         (#PCDATA|br|ex|i|it|plain|xex)*>
		"bar",//       EMPTY>
		"bio",//       (#PCDATA|booki|br|city|date|edi|er|it|note|org|pbr|persfn|person|pr|publ|sc)*>
		"biography",// (#PCDATA)>
		"blacklettertype",// (#PCDATA)>
		"body",//     (note|p)*>
		"bold",//      (#PCDATA|xex)*>
		"boldfacetype",// (#PCDATA)>
		"book",//      (#PCDATA)>
		"booki",//     (#PCDATA)>
		"bourgeoistype",// (#PCDATA|xex)*>
		"boxtype",//   (#PCDATA)>
		"br",//        EMPTY>
		"branchof",//  (#PCDATA)>
		"caption",//   (#PCDATA|er|note|qex|spn|xex)*>
		"cas",//       (#PCDATA)>
		"causedby",//  (#PCDATA|gen|spn)*>
		"causedbyp",// (spn)*>
		"causes",//    (#PCDATA)>
		"cd",//        (#PCDATA|a|abbr|altname|altsp|ant|as|asp|au|book|br|cas|chform|chname|city|class|cnvto|col|compof|contr|country|cp|cref|ecol|er|ets|etsep|ety|ex|examp|exp|fam|fexp|figref|fld|frac|fract|gen|geog|grk|illust|isa|it|itran|mark|mathex|matrix2x5|member|members|note|ord|part|partof|parts|pbr|persfn|person|pluf|pos|pr|prod|prodby|prodmac|ptcl|sc|sd|ship|sig|source|specif|spn|state|styp|stype|subclass|supr|table|trademark|universbold|var|varn|xex)*>
		"cd2",//      (#PCDATA|au|cd|ex)*>
		"centered",//  (#PCDATA|point16|point18|point26)*>
		"chform",//    (#PCDATA|sups)*>
		"chname",//    (#PCDATA|it)*>
		"chreact",//   (#PCDATA)>
		"city",//      (#PCDATA|etsep)*>
		"clarendontype",// (#PCDATA)>
		"class",//     (#PCDATA|er)*>
		"cnvto",//     (#PCDATA)>
		"col",//       (#PCDATA|b|cd|it|plain)*>
		"colbreak",//  EMPTY>
		"colf",//      (#PCDATA)>
		"colheads",//  (coltitle)*>
		"colp",//      (#PCDATA)>
		"colret",//    EMPTY>
		"coltitle",//  (#PCDATA)>
		"column1",//   (#PCDATA|vertical)*>
		"comm",//      (#PCDATA)>
		"comp",//      (#PCDATA)>
		"company",//   (#PCDATA)>
		"compof",//    (#PCDATA)>
		"conjf",//     (#PCDATA|i|pr)*>
		"conseq",//    (#PCDATA)>
		"consof",//    (#PCDATA)>
		"contains",//  (#PCDATA)>
		"contr",//     (#PCDATA|colf|cref|er|i)*>
		"contxt",//    (#PCDATA)>
		"corpn",//     (#PCDATA|etsep)*>
		"corr",//      (#PCDATA)>
		"country",//   (#PCDATA)>
		"cp",//        (#PCDATA)>
		"cref",//      (#PCDATA|i)*>
		"cs",//        (#PCDATA|altname|altsp|au|bold|br|cd|cd2|chreact|col|cref|def|er|ety|ex|fld|it|mark|mcol|note|pbr|plu|pluf|pos|pr|q|qau|qex|rj|sd|see|sn|source|specif|spn|stype|subclass|table|wordforms|xex)*>
		"ct",//        (#PCDATA)>
		"date",//      (#PCDATA)>
		"datey",//     (#PCDATA)>
		"decf",//      (#PCDATA)>
		"def",//       (#PCDATA|a|abbr|altname|altnpluf|altsp|ant|as|asp|au|b|book|booki|boxtype|br|branchof|causedby|causedbyp|causes|cd|chform|chname|chreact|city|class|cnvto|col|colf|colp|comp|company|compof|contains|contr|corpn|corr|country|cref|date|datey|divof|ecol|er|ets|etsep|ety|ex|examp|exp|fam|fld|frac|fract|funct|gen|geog|grk|grp|hascons|haspart|hw|hwf|hypen|i|illu|inv|iref|isa|it|itrans|jour|kingdom|mark|markp|mathex|mcol|member|memberof|members|membof|methodfor|mord|note|ord|org|part|partof|parts|persfn|person|phylum|plu|pluf|plw|pos|pr|prod|prodby|ptcl|publ|q|qex|recipr|river|rj|sansserif|sc|sd|sfield|sig|simto|singf|singw|sn|source|specif|spn|stage|stageof|state|street|styp|stype|sub|subclass|subfam|subord|suborder|subphylum|subs|subtypes|sup|sups|tr|tradename|tran|unit|universbold|usage|usedby|usedfor|uses|var|varn|wordforms|xex|xlati)*>
		"def2",//      (#PCDATA|au|def|ety|fld|hw|mark|plu|pluf|pos|pr|rj|sd|see|singf|specif|xex)*>
		"dictionary",// (body|front)*>
		"div0",//      (H1|H2|ab.entry|au.entry|p)*>
		"divof",//     (org)*>
		"ecol",//      (b)*>
		"edi",//       (#PCDATA)>
		"emits",//     (#PCDATA)>
		"englishtype",// (#PCDATA|sc)*>
		"er",//        (#PCDATA|as|it|pos)*>
		"ets",//       (#PCDATA|gen|grk|spn)*>
		"etsep",//     (#PCDATA|plain)*>
		"ety",//       (#PCDATA|altname|as|asp|au|b|booki|city|company|contr|corpn|country|cref|er|ets|etsep|ex|film|frac|fu|gen|grk|it|itran|itrans|org|persfn|person|pluf|plw|pos|pr|publ|sc|sd|sig|sn|spn|title|tr|tran|xex|xlati)*>
		"ex",//        (#PCDATA|plain|subs)*>
		"examp",//     (#PCDATA)>
		"exp",//       (#PCDATA|frac)*>
		"extendedtype",// (#PCDATA)>
		"fam",//       (#PCDATA)>
		"fexp",//      (#PCDATA|exp)*>
		"figcap",//    (#PCDATA)>
		"figref",//    (#PCDATA)>
		"figtitle",//  (#PCDATA)>
		"figure",//    (figtitle)*>
		"film",//      (#PCDATA)>
		"fld",//       (#PCDATA|er|it|xex)*>
		"fr",//        (#PCDATA)>
		"frac",//      (#PCDATA)>
		"fract",//     (#PCDATA|it|mathex)*>
		"frenchelzevirtype",// (#PCDATA)>
		"front",//     (div0)*>
		"fu",//        (#PCDATA)>
		"funct",//     (it|sups)*>
		"gen",//       (#PCDATA|etsep)*>
		"geog",//      (#PCDATA)>
		"germantype",// (point10)*>
		"gothictype",// (#PCDATA)>
		"greatprimertype",// (#PCDATA)>
		"grk",//       (#PCDATA)>
		"grp",//       (#PCDATA)>
		"h1",//        (#PCDATA)>
		"h2",//        (#PCDATA)>
		"hascons",//   (#PCDATA|er)*>
		"haspart",//   (#PCDATA)>
		"headrow",//   (item|mitem)*>
		"hw",//        (#PCDATA|sub|supr)*>
		"hwf",//       (#PCDATA)>
		"hypen",//     (#PCDATA)>
		"i",//         (#PCDATA)>
		"illu",//      (#PCDATA|er|ex|spn)*>
		"illust",//    (#PCDATA|matrix|xex)*>
		"img",//       EMPTY>
		"intensi",//   (#PCDATA)>
		"inv",//       (#PCDATA)>
		"iref",//      (#PCDATA)>
		"isa",//       (#PCDATA|cref|ecol|fam|phylum)*>
		"it",//        (#PCDATA|exp)*>
		"item",//      (#PCDATA|i|pre)*>
		"itran",//     (#PCDATA)>
		"itrans",//    (#PCDATA)>
		"jour",//      (#PCDATA)>
		"kingdom",//   (#PCDATA)>
		"longprimertype",// (#PCDATA|it)*>
		"mark",//      (#PCDATA|as|er|ex|i|it|plain|plw|pos|singf|xex)*>
		"markp",//     (#PCDATA)>
		"mathex",//    (#PCDATA|exp|frac|fract|it|ratio|root|sub|subs|vinc)*>
		"matrix",//    (row)*>
		"matrix2x5",// (row)*>
		"mcol",//      (#PCDATA|altname|au|cd|chform|col|ety|fld|i|it|mark|note|pos|pr|prod|sd|spn|xex)*>
		"member",//    (#PCDATA|spn)*>
		"memberof",//  (org)*>
		"members",//   (#PCDATA)>
		"membof",//    (#PCDATA)>
		"method",//    (#PCDATA|def|sd)*>
		"methodfor",// (#PCDATA)>
		"mhw",//      (#PCDATA|ety|hw|i|it|mark|plu|pluf|pos|pr|vmorph|xex)*>
		"miniontype",// (#PCDATA|it)*>
		"mitem",//     (#PCDATA|colret|item|str)*>
		"mord",//      (#PCDATA|er|ets|pos|xex)*>
		"mstypec",//   (#PCDATA|it|stypec)*>
		"mtable",//    (row)*>
		"musfig",//    (#PCDATA)>
		"nmorph",//    (#PCDATA|decf|er|plu|pos|pr)*>
		"nonpareiltype",// (#PCDATA|ex)*>
		"note",//      (#PCDATA|H1|I|TITLE|a|abbr|altname|altsp|ant|as|asp|au|b|bold|booki|bourgeoistype|br|causedby|cd|centered|chform|chname|chreact|city|class|col|colf|consof|contr|country|cref|ecol|emits|englishtype|er|ets|etsep|ety|ex|examp|exp|extendedtype|fam|figref|fld|frac|fract|gen|geog|germantype|gothictype|grk|h2|hascons|hw|i|img|intensi|inv|iref|isa|it|longprimertype|mark|mathex|matrix|mcol|member|membof|musfig|nonpareiltype|ord|part|partof|parts|pbr|perf|persfn|person|phylum|picatype|pluf|plw|pos|pr|pre|prod|prodby|ptcl|q|qau|qex|rj|sc|sd|see|sig|singw|smpicatype|sn|source|spn|stage|state|styp|stype|suborder|subphylum|subs|subtypes|table|title|tt|unit|universbold|uses|varn|xex)*>
		"oldenglishtype",// (#PCDATA)>
		"oldstyletype",// (#PCDATA)>
		"ord",//       (#PCDATA)>
		"org",//       (#PCDATA)>
		"p",//         (#PCDATA|abbr|altname|altsp|amorph|ant|as|au|b|bio|biography|bold|br|caption|cd|centered|col|colbreak|comm|conseq|contr|contxt|cref|cs|def|def2|er|ets|ety|ex|figcap|figure|fld|fr|fu|gen|grk|h1|hascons|hw|hypen|illu|illust|it|mark|mcol|method|mhw|miniontype|mord|mtable|nmorph|note|pearltype|person|plu|pluf|plw|pos|pr|pre|q|qau|qex|refs|rj|sd|see|simto|sing|singf|sn|source|specif|stype|subtypes|syn|table|title|usage|vmorph|wn16s|wnote|wns|wordforms|xex)*>
		"part",//      (#PCDATA|er)*>
		"partof",//    (#PCDATA|er)*>
		"parts",//     (#PCDATA)>
		"pbr",//       EMPTY>
		"pearltype",// (#PCDATA|ex)*>
		"perf",//      (#PCDATA)>
		"persfn",//    (#PCDATA|etsep|it|xex)*>
		"person",//    (#PCDATA|altname|b|ets|etsep|ex|it)*>
		"phylum",//    (#PCDATA|er)*>
		"picatype",//  (#PCDATA|it)*>
		"plain",//     (#PCDATA)>
		"plu",//       (#PCDATA|au|col|er|hw|i|it|mark|note|pluf|plw|pos|pr|xex)*>
		"pluf",//      (#PCDATA)>
		"plw",//       (#PCDATA|abbr|it|pr)*>
		"point1",//    (bar)*>
		"point1.5",//  (bar)*>
		"point10",//   (#PCDATA|bar)*>
		"point11",//   (bar)*>
		"point12",//   (bar)*>
		"point14",//   (bar)*>
		"point16",//   (#PCDATA|bar)*>
		"point18",//   (bar|greatprimertype)*>
		"point2",//    (bar)*>
		"point2.5",//  (bar)*>
		"point20",//   (bar)*>
		"point26",//   (#PCDATA)>
		"point3",//    (bar)*>
		"point3.5",//  (bar)*>
		"point4",//    (bar)*>
		"point4.5",//  (bar)*>
		"point5",//    (bar)*>
		"point5.5",//  (bar)*>
		"point6",//    (bar)*>
		"point7",//    (bar)*>
		"point8",//    (bar)*>
		"point9",//    (bar)*>
		"pos",//       (#PCDATA|altsp|i|plain)*>
		"pr",//        (#PCDATA|er|i|it|mark|pos|xex)*>
		"pre",//       (#PCDATA|b|br|colbreak|colf|headrow|row|source|table|tt)*>
		"prod",//      (#PCDATA|col|ecol)*>
		"prodby",//    (#PCDATA|gen|spn)*>
		"prodmac",//  (#PCDATA)>
		"ptcl",//      (#PCDATA)>
		"publ",//      (#PCDATA|it)*>
		"q",//         (#PCDATA|altname|au|br|city|company|corpn|country|er|ex|examp|frac|grk|h2|it|mark|note|org|persfn|person|publ|qau|qex|qpers|qperson|rj|sb|sc|spn|stype|xex)*>
		"qau",//       (#PCDATA|er|publ)*>
		"qex",//       (#PCDATA|plain)*>
		"qpers",//     (#PCDATA)>
		"qperson",//   (#PCDATA)>
		"ratio",//     (#PCDATA)>
		"recipr",//    (#PCDATA)>
		"ref",//       (#PCDATA)>
		"refs",//      (#PCDATA|booki|br|person)*>
		"river",//     (#PCDATA)>
		"rj",//        (#PCDATA|au|mark|qau)*>
		"root",//      (#PCDATA|exp)*>
		"row",//       (#PCDATA|antiquetype|blacklettertype|boldfacetype|br|clarendontype|frenchelzevirtype|gothictype|item|oldenglishtype|oldstyletype|scripttype|typewritertype)*>
		"sansserif",// (#PCDATA)>
		"sb",//        EMPTY>
		"sc",//        (#PCDATA)>
		"scripttype",// (#PCDATA)>
		"sd",//        (#PCDATA)>
		"see",//       (#PCDATA|ant|cref|er|ex|pos|simto|xex)*>
		"sfield",//    (#PCDATA)>
		"ship",//      (#PCDATA)>
		"sig",//       (#PCDATA|chform|i|it)*>
		"simto",//     (#PCDATA|persfn)*>
		"sing",//      (#PCDATA|it|pr|singf|singw)*>
		"singf",//     (#PCDATA)>
		"singw",//     (#PCDATA|it)*>
		"smpicatype",// (#PCDATA|it)*>
		"sn",//        (#PCDATA)>
		"source",//    (#PCDATA)>
		"specif",//    (#PCDATA)>
		"spn",//       (#PCDATA|ets|i|plain)*>
		"stage",//     (#PCDATA)>
		"stageof",//   (gen)*>
		"state",//     (#PCDATA)>
		"str",//       (td)*>
		"street",//    (#PCDATA)>
		"styp",//      (#PCDATA|class|col|cref|ecol|er|ex|gen|hw|ord|spn)*>
		"stype",//     (#PCDATA|col|colf|ecol|er|ex|gen|it|ord|spn|sub|suborder|subs)*>
		"stypec",//    (#PCDATA)>
		"sub",//       (#PCDATA)>
		"subclass",//  (#PCDATA|er|stype)*>
		"subfam",//    (#PCDATA)>
		"subord",//    (#PCDATA)>
		"suborder",//  (#PCDATA)>
		"subphylum",// (#PCDATA|er)*>
		"subs",//      (#PCDATA|frac)*>
		"subtypes",//  (#PCDATA|cd|col|mcol|mstypec|pbr|sd|spn|stypec|xex)*>
		"sup",//       (#PCDATA)>
		"supr",//      (#PCDATA)>
		"sups",//      (#PCDATA)>
		"syn",//       (#PCDATA|as|au|b|chname|class|er|fam|gen|ord|org|person|pos|sd|spn|subclass|subfam|subord|subs|varn|xex)*>
		"table",//     (#PCDATA|CAPTION|altname|br|caption|centered|colheads|column1|er|frac|it|pbr|point1|point1.5|point10|point11|point12|point14|point16|point18|point2|point2.5|point20|point3|point3.5|point4|point4.5|point5|point5.5|point6|point7|point8|point9|pre|row|source|tabtitle|title|tr|ttitle|xex)*>
		"tabtitle",//  (#PCDATA)>
		"td",//        (#PCDATA)>
		"th",//        (#PCDATA|i)*>
		"title",//     (#PCDATA)>
		"tr",//        (#PCDATA|mitem|str|td|th)*>
		"trademark",// (#PCDATA)>
		"tradename",// (#PCDATA)>
		"tran",//      (#PCDATA)>
		"tt",//        (#PCDATA|b|br|colf|ct|er|it|pbr|sc)*>
		"ttitle",//    (#PCDATA)>
		"typewritertype",// (#PCDATA)>
		"uex",//       (#PCDATA)>
		"unit",//      (#PCDATA)>
		"universbold",// (#PCDATA)>
		"usage",//     (#PCDATA|as|asp|au|br|contr|cref|def|er|ets|ex|it|pbr|person|pos|q|ref|rj|singw|sn|source|stype|uex|xex)*>
		"usedby",//    (#PCDATA|er)*>
		"usedfor",//   (#PCDATA)>
		"uses",//      (#PCDATA)>
		"var",//       (#PCDATA)>
		"varn",//      (#PCDATA)>
		"vertical",//  (#PCDATA)>
		"vinc",//      (#PCDATA)>
		"vmorph",//    (#PCDATA|altsp|au|conjf|er|ets|i|it|mark|pos|pr|usage|xex)*>
		"wf",//        (#PCDATA)>
		"wn16s",//     (#PCDATA)>
		"wnote",//     (#PCDATA)>
		"wns",//       (#PCDATA)>
		"wordforms",// (#PCDATA|au|col|def|er|ety|fld|hw|it|mark|pos|pr|rj|wf)*>
		"xex",//       (#PCDATA)>
		"xlati" //     (#PCDATA)>
	};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		        
        try {
        	        	        	
        	for(String fileName: files){
        		
        		System.out.println(fileName);
        
        		DBManagerOracle dbManager = new DBManagerOracle();        		
        		
        		// 1. Read in each *.xml file into a document
        		
        		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    			Document doc = docBuilder.parse (new File(DICTIONARY_FILES_FOLDER + fileName));            			
    			doc.getDocumentElement().normalize();
    			
    			NodeList wordClobs = doc.getElementsByTagName("p");
    			
    			// get the nodes under <p>, then find all the child tags under for each
    			System.out.println("Number of word clobs for " + fileName + ": " + wordClobs.getLength());
    			   	
    			String previousHWVal = null;
    			
    			for(int x = 0; x < wordClobs.getLength(); x++){
    				
                    Node wordNode = wordClobs.item(x);
                    
                    if(wordNode.getNodeType() == Node.ELEMENT_NODE){

                    	Element wordElement = (Element)wordNode;
                    	
                    	map = new HashMap<String, String>();
                    	
                    	// get the tags under the word i.e. inside <p>... </p>
                    	
                    	for(String tagName: tagNames){
                    		
                    		String val = getNodeValue(wordElement, tagName);
                    		                    		
                    		if(val!=null){                    			
                    			
                    			map.put(tagName, val);
                    		}                    		                    		
                    	}

                    	// make sure there is a headword to represent this
                    	if(!map.containsKey("hw")){
                    		map.put("hw", previousHWVal);
                    	} else {
                    		previousHWVal = map.get("hw");
                    	}  
                    	
                    	System.out.println(previousHWVal);
                    	
                    	if(previousHWVal != null){
                    		
                    		map.put("word", previousHWVal.replace("`","").replace("\"","").replace("*",""));
                    	}                                    
                    	
                    	String insertColumns = "";
                    	String insertValues = "";
                    	String insertSQL = "INSERT INTO WORDS(?columns?) VALUES(?values?)";
            			
                    	for(Entry<String, String> mappedTag: map.entrySet()){
            				
            				// TODO: Generate INSERT STATEMENT FOR DATABASE SPECIFIED and RUN IT
            				insertColumns += mappedTag.getKey() + "_" + ","; 
            				if(mappedTag.getValue() != null){
            					insertValues += "'" + mappedTag.getValue().replace("'", "' || CHR(39) || '") + "'" + ",";	
            				} else {
            					insertValues += "'',";
            				}
            				
            				            				
            				//System.out.println(mappedTag.getKey() + ": " + mappedTag.getValue());            				            				
            			}
            			
            			insertColumns = insertColumns.substring(0, insertColumns.lastIndexOf(","));
            			insertValues = insertValues.substring(0, insertValues.lastIndexOf(","));  //escapeSQLCharacters(insertValues.substring(0, insertValues.lastIndexOf(",")));
            			
            			try{
        					insertSQL = insertSQL.replace("?columns?", insertColumns).replace("?values?", insertValues);
        					//System.out.println(insertSQL);
        					ResultSet results = dbManager.executeQuery(insertSQL);
        					//dbManager.closeConnection();
        					//dbManager.closeConnection();
            				
            			} catch (Exception ex){
            				
            				System.out.println("PROBLEM with query: " + insertSQL);
            			}

                    	
                    }                     
    				
    			}
   			
    			
    			// 2. Break the string up into a list/array/object list
    			// 3. Generate the SQL to run in MySQL
    			// 4. Connect to MySQL and execute the statements
    			
    			System.out.println("Finished processing " + fileName);	    			
        	}     
        	
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}
	
//	public static String escapeSQLCharacters(String query){
//		
//		String[] specialChars = {"'"};  // add more if need be
//		
//		for(String specialChar: specialChars){
//			
//			if(query.contains(specialChar)){
//				
//				query = query.replace(specialChar, "' || CHR(39) || '");
//			}
//		}		
//		
//		return query;
//	}
	
//	public static void createFiles(){
//		
//		for(String unconvertedFileName : unconvertedFiles){
//			
//			File file1 = new File(DICTIONARY_FILES_FOLDER + "xgcide.xml");
//			File file2 = new File(DICTIONARY_FILES_FOLDER + unconvertedFileName);
//			
//			File file3 = new File(DICTIONARY_FILES_FOLDER + "x" + unconvertedFileName);
//			
//			try{
//				String fileString1 = FileUtils.readFileToString(file1);
//				String fileString2 = FileUtils.readFileToString(file2);
//		        
//				String fileString3 = fileString1.replace("[[HERE]]", fileString2);
//				
//				FileUtils.writeStringToFile(file3,fileString3);
//				
//		        
//		    }catch(IOException e){
//		        e.printStackTrace();
//		    }   
//			
//		}
//	}
	
	public static String getNodeValue(Element parentElement, String tagName){
		
        NodeList tagElements = parentElement.getElementsByTagName(tagName);
        
        String val = "";

        for(int y=0; y<tagElements.getLength(); y++){
        	
        	Node tagNode = tagElements.item(y).getFirstChild();
        	
        	while(tagNode != null){
        		
        		if(tagNode.getNodeType() == Node.TEXT_NODE){
        			
        			if(val.equals("")){        				
        				val = tagNode.getNodeValue();
        			} else {
        				val += " " + tagNode.getNodeValue();
        			}
        			
        		}else if(tagNode.getNodeType() == Node.ELEMENT_NODE){
        			
        			val += tagNode.getTextContent();
        			
        		}
        		
        		tagNode = tagNode.getNextSibling();
        			
        	}
        	
        }
        
        if (val.equals("")){
        	return null;
        }
        return val;
	}

}

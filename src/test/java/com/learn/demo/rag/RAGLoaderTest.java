package com.learn.demo.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.Map;

/**
 * @ClassName -> RAGLoaderTest
 * @Description
 * @Author soso
 * @Date 2025/7/7 15:14 星期一
 * @Version 1.0
 */
@SpringBootTest
public class RAGLoaderTest {

    /**
     * 测试读取文档
     */
    @Test
    public void testReadDocument() {

        String path1 = "C:\\Users\\maxel-de1002\\IdeaProjects\\spring-ai\\ai-langchain4j\\src\\main\\resources\\text\\医院介绍.txt";
        // 读取文档
//        Document document = FileSystemDocumentLoader.loadDocument(path1);
//        System.out.println(document.text());


        // 加载单个文档
//        System.out.println("加载单个文档==========================================================");
//        Document document1 = FileSystemDocumentLoader.loadDocument(path1, new TextDocumentParser());
//        System.out.println(document1.text());


        String path2 = "D:\\BaiduNetdiskDownload\\硅谷小智\\knowledge";
        // 加载目录下的所有文档
//        System.out.println("加载目录下的所有文档==========================================================");
//        List<Document> documents = FileSystemDocumentLoader.loadDocuments(path2, new TextDocumentParser());
//        for (Document document2 : documents) {
//            System.out.println(document2.text());
//        }

        // 加载目录下指定类型的文档txt
//        System.out.println("加载目录下指定类型的文档txt==========================================================");
//        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
//        List<Document> documents1 = FileSystemDocumentLoader.loadDocuments(path2, pathMatcher, new TextDocumentParser());
//        for (Document document2 : documents1) {
//            System.out.println(document2.text());
//        }

        // 加载指定目录及其子目录下的所有文档
        System.out.println("加载指定目录及其子目录下的所有文档==========================================================");
        List<Document> documents2 = FileSystemDocumentLoader.loadDocumentsRecursively(path2, new TextDocumentParser());
        for (Document document2 : documents2) {
            System.out.println(document2.text());
        }
    }

    /**
     * 测试读取pdf。
     * 来自 langchain4j-document-parser-apache-tika 模块的 Apache Tika 文档解析器
     * （ApacheTikaDocumentParser），它可以自动检测并解析几乎所有现有的文件格式。
     */
    @Test
    public void testReadPdf() {
        String path = "D:\\BaiduNetdiskDownload\\硅谷小智\\knowledge\\科室信息.pdf";
        Document document = FileSystemDocumentLoader.loadDocument(path, new ApacheTikaDocumentParser());
        System.out.println(document.text());
    }

}

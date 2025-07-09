package com.learn.demo.rag;

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import com.alibaba.dashscope.tokenizers.QwenTokenizer;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByCharacterSplitter;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenCountEstimator;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.IngestionResult;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenCountEstimator;
import java.util.List;

/**
 * @ClassName -> RAGSplitterTest
 * @Description 文档分隔器测试类
 * @Author soso
 * @Date 2025/7/7 17:15 星期一
 * @Version 1.0
 */
@SpringBootTest
public class RAGSplitterTest {

    //    按段落文档分割器（DocumentByParagraphSplitter）

    //    按行文档分割器（DocumentByLineSplitter）
    //    按句子文档分割器（DocumentBySentenceSplitter）
    //    按单词文档分割器（DocumentByWordSplitter）
    //    按字符文档分割器（DocumentByCharacterSplitter）
    //    按正则表达式文档分割器（DocumentByRegexSplitter）
    //    递归分割：DocumentSplitters.recursive (...)

    @Test
    public void testReadDocumentIngestStore() {
        String path = "D:\\BaiduNetdiskDownload\\硅谷小智\\knowledge\\人工智能.md";
        // 加载文档
        Document document = FileSystemDocumentLoader.loadDocument(path, new TextDocumentParser());
        // 基于内存的嵌入模型向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // 将文档存储在内存向量数据库中
        IngestionResult result = EmbeddingStoreIngestor.ingest(document, embeddingStore);
        System.out.println(result);
        // 查看向量数据库内容
        System.out.println(embeddingStore);
    }

    /**
     * 测试文档分隔器，使用分词器拆分文档后存入内存向量库中
     */
    @Test
    public void testDocumentSplitters() {
        String path = "D:\\BaiduNetdiskDownload\\硅谷小智\\knowledge\\人工智能.md";
        // 加载文档
        Document document = FileSystemDocumentLoader.loadDocument(path, new TextDocumentParser());
        // 按照token数进行拆分，最大300个token，两个段落之间有30个token的重叠，使用HuggingFace的token分词器
        DocumentByCharacterSplitter tokenSplitter = new DocumentByCharacterSplitter(
                300,
                30,
                new HuggingFaceTokenCountEstimator());
        // 按照字符数进行拆分，最大300个字符，两个段落之间有30个字符的重叠
        DocumentByCharacterSplitter strSplitter = new DocumentByCharacterSplitter(
                300,
                30);

        // 基于内存的嵌入模型向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        // 使用分词器拆分文档后存入内存向量库中
        IngestionResult result = EmbeddingStoreIngestor.builder()
                .documentSplitter(tokenSplitter)
                .embeddingStore(embeddingStore)
                .build()
                .ingest(document);
        System.out.println(result);
        System.out.println(embeddingStore);
    }

    @Test
    public void testTokenCount() {
        String str = "这是一句用于测试token长度的字符串。";
        UserMessage userMessage = UserMessage.userMessage(str);
        //计算 token 长度
        HuggingFaceTokenCountEstimator tokenCountEstimator = new HuggingFaceTokenCountEstimator();
        int count = tokenCountEstimator.estimateTokenCountInMessage(userMessage);
        System.out.println("token长度：" + count);
    }

}

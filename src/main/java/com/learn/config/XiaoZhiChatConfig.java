package com.learn.config;

import com.learn.store.MongoDBChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ClassName -> XiaoZhiChatConfig
 * @Description
 * @Author soso
 * @Date 2025/7/3 17:15 星期四
 * @Version 1.0
 */
@Configuration
public class XiaoZhiChatConfig {

    /**
     * 构建小智医疗助手使用的聊天记忆配置对象
     *
     * @param mongoDBChatMemoryStore mongodb聊天记忆存储
     * @return 小智医疗助手使用的聊天记忆配置对象
     */
    @Bean
    public ChatMemoryProvider xiaoZhiMessageWindowChatMemory(MongoDBChatMemoryStore mongoDBChatMemoryStore) {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(mongoDBChatMemoryStore)
                .build();
    }

    /**
     * 构建小智医疗助手使用的内容检索器
     *
     * @return 小智医疗助手使用的内容检索器
     */
    @Bean
    public ContentRetriever contentRetrieverXiaoZhi() {
        String prePath = "D:\\BaiduNetdiskDownload\\硅谷小智\\knowledge\\";
        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析
        Document document1 = FileSystemDocumentLoader.loadDocument(prePath + "医院信息.md");
        Document document2 = FileSystemDocumentLoader.loadDocument(prePath + "科室信息.md");
        Document document3 = FileSystemDocumentLoader.loadDocument(prePath + "神经内科.md");
        List<Document> documents = List.of(document1, document2, document3);
        //使用内存向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>
                ();
        //使用默认的文档分割器
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
        //从嵌入存储（EmbeddingStore）里检索和查询内容相关的信息
        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }

    /**
     * 构建内容检索器，基于Pinecone向量数据库
     *
     * @param embeddingModel 内嵌模型
     * @param embeddingStore 向量数据库
     * @return 内容检索器
     */
    @Bean
    public ContentRetriever contentRetriever4Pinecone(EmbeddingModel embeddingModel, EmbeddingStore<TextSegment> embeddingStore) {
        EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingModel(embeddingModel) // 设置用于生成嵌入向量的嵌入模型
                .embeddingStore(embeddingStore) // 指定要使用的嵌入存储
                .maxResults(2) // 设置最大检索结果数量，这里表示最多返回 2 条匹配结果
                .minScore(0.8) // 设置最小得分阈值，只有得分大于等于 0.8 的结果才会被返回
                .build(); // 构建最终的 EmbeddingStoreContentRetriever 实例
        return retriever;
    }

}

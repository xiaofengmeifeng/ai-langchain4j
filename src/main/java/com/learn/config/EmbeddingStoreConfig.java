package com.learn.config;

import dev.langchain4j.community.model.dashscope.QwenTokenCountEstimator;
import dev.langchain4j.data.document.splitter.DocumentByCharacterSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName -> EmbeddingStoreConfig
 * @Description
 * @Author soso
 * @Date 2025/7/8 15:58 星期二
 * @Version 1.0
 */
@Configuration
public class EmbeddingStoreConfig {

    @Value("${pinecone.api-key}")
    private String pineconeApiKey;

    @Value("${pinecone.index}")
    private String pineconeIndex;

    @Value("${pinecone.name-space}")
    private String pineconeNameSpace;

    @Value("${pinecone.cloud}")
    private String pineconeCloud;

    @Value("${pinecone.region}")
    private String pineconeRegion;

    @Value("${langchain4j.community.dashscope.embedding-model.api-key}")
    private String qwenApiKey;

    @Value("${langchain4j.community.dashscope.embedding-model.model-name}")
    private String qwenModelName;

    /**
     * 文本嵌入向量存储
     *
     * @return 文本嵌入向量存储
     */
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(EmbeddingModel embeddingModel) {
        PineconeEmbeddingStore embeddingStore = PineconeEmbeddingStore.builder()
                .apiKey(pineconeApiKey)
                .index(pineconeIndex) // 如果指定的索引不存在，将创建一个新的索引
                .nameSpace(pineconeNameSpace) // 如果指定的名称空间不存在，将创建一个新的名称空间
                .createIndex(PineconeServerlessIndexConfig.builder()
                                     .cloud(pineconeCloud) // 指定索引部署在 AWS 云服务上。
                                     .region(pineconeRegion) // 指定索引所在的 AWS 区域为 us-east-1。
                                     .dimension(embeddingModel.dimension()) // 指定索引的向量维度，该维度与 embeddedModel 生成的向量维度相同。
                                     .build())
                .build();
        return embeddingStore;
    }

    @Bean
    public QwenTokenCountEstimator qwenTokenCountEstimator() {
        return new QwenTokenCountEstimator(qwenApiKey, qwenModelName);
    }


    @Bean
    public DocumentByCharacterSplitter documentByCharacterSplitter(QwenTokenCountEstimator qwenTokenCountEstimator) {
        return new DocumentByCharacterSplitter(300, // 最大300token
                                               30,// 30token重合
                                               qwenTokenCountEstimator);
    }

}

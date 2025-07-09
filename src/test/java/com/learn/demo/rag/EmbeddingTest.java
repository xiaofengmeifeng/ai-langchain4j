package com.learn.demo.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByCharacterSplitter;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenCountEstimator;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.*;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @ClassName -> EmbeddingTest
 * @Description
 * @Author soso
 * @Date 2025/7/8 15:04 星期二
 * @Version 1.0
 */
@SpringBootTest
public class EmbeddingTest {

    @Autowired
    private EmbeddingModel embeddingModel;

    /**
     * 测试向量维度
     */
    @Test
    public void test() {
        String text = "你好";
        Response<Embedding> embed = embeddingModel.embed(text);
        System.out.println("向量维度：" + embed.content().vector().length);
        System.out.println("向量内容：" + embed);
    }

    @Autowired
    private EmbeddingStore embeddingStore;

    @Test
    public void testVectoryStore() {
        //将文本转换成向量
        TextSegment segment1 = TextSegment.from("我喜欢羽毛球");
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        //存入向量数据库
        embeddingStore.add(embedding1, segment1);
        TextSegment segment2 = TextSegment.from("今天天气很好");
        Embedding embedding2 = embeddingModel.embed(segment2).content();
        embeddingStore.add(embedding2, segment2);
    }
    /**
     * Pinecone-相似度匹配
     */
    @Test
    public void embeddingSearch() {
        //提问，并将问题转成向量数据
        Embedding queryEmbedding = embeddingModel.embed("今天天气怎么样？").content();
        //创建搜索请求对象
        EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(1) //匹配最相似的一条记录
                //.minScore(0.8)
                .build();
        EmbeddingSearchResult searchResult = embeddingStore.search(searchRequest);
        System.out.println(searchResult);
        //searchResult.matches()：获取搜索结果中的匹配项列表。
        //.get(0)：从匹配项列表中获取第一个匹配项
        EmbeddingMatch<TextSegment> embeddingMatch = (EmbeddingMatch<TextSegment>) searchResult.matches().get(0);
        //获取匹配项的相似度得分
        System.out.println(embeddingMatch.score()); // 0.9041835588525624
        //返回文本结果：今天天气很好
        System.out.println(embeddingMatch.embedded().text());
    }

    /**
     * 按照token数进行拆分，最大300个token，两个段落之间有30个token的重叠，使用HuggingFace的token分词器
     */
    @Autowired
    private DocumentByCharacterSplitter documentByCharacterSplitter;

    /**
     * 测试文档分隔器，使用分词器拆分文档后存入内存向量库中，向量库为cloud远程库，名称：Pinecone
     */
    @Test
    public void testUploadDocument2Pinecone(){
        String prePath = "D:\\BaiduNetdiskDownload\\硅谷小智\\knowledge\\";
        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析
        Document document1 = FileSystemDocumentLoader.loadDocument(prePath + "医院信息.md");
        Document document2 = FileSystemDocumentLoader.loadDocument(prePath + "科室信息.md");
        Document document3 = FileSystemDocumentLoader.loadDocument(prePath + "神经内科.md");
        List<Document> documents = List.of(document1, document2, document3);

        EmbeddingStoreIngestor.builder()
//                .documentSplitter(documentByCharacterSplitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build()
                .ingest(documents);
    }

}

package rover.ai;

import java.util.List;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;

/**
 * Helper class for interacting with the AI model.
 */
public class AiHelper {
    private final ChatModel model;

    /**
     * Constructs an AiHelper with the necessary configuration for the AI model.
     */
    public AiHelper() {
        String apiKey = System.getenv("LLM_API_KEY");
        String apiUrl = System.getenv("LLM_API_URL");
        String modelName = System.getenv("LLM_MODEL_NAME");
        this.model = OpenAiChatModel.builder()
            .apiKey(apiKey)
            .baseUrl(apiUrl)
            .modelName(modelName)
            .build();
    }

    public ChatResponse getAiResponse(String systemPrompt, String userPrompt) {
        ChatRequest req = ChatRequest.builder()
            .messages(List.of(
                SystemMessage.from(systemPrompt),
                UserMessage.from(userPrompt)
            ))
            .build();

        ChatResponse res = model.chat(req);
        return res;
    }
}

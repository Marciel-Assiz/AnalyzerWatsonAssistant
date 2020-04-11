import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.cloud.sdk.core.service.exception.NotFoundException;
import com.ibm.cloud.sdk.core.service.exception.RequestTooLargeException;
import com.ibm.cloud.sdk.core.service.exception.ServiceResponseException;
import com.ibm.watson.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.natural_language_understanding.v1.model.CategoriesOptions;
import com.ibm.watson.natural_language_understanding.v1.model.ConceptsOptions;
import com.ibm.watson.natural_language_understanding.v1.model.EmotionOptions;
import com.ibm.watson.natural_language_understanding.v1.model.Features;
import com.ibm.watson.natural_language_understanding.v1.model.RelationsOptions;
import com.ibm.watson.natural_language_understanding.v1.model.SentimentOptions;

public class Principal {
	public static void main(String[] args) {
		try {
			  // Invoke a Natural Language Understanding method
			IamAuthenticator authenticator = new IamAuthenticator("b6GgjzSLHSewPh0mMNPGkS1mSsNNSBd8E_cyrcqn4VBd");
			NaturalLanguageUnderstanding naturalLanguageUnderstanding = new NaturalLanguageUnderstanding("2019-07-12", authenticator);
			naturalLanguageUnderstanding.setServiceUrl("https://api.us-south.natural-language-understanding.watson.cloud.ibm.com/instances/337fd35e-475e-46ef-b762-808f5a8b15ae");

			String text = "Hoje eu estou muito triste por causa das mortes causadas pelo Covid-19.";

			CategoriesOptions categories	= new CategoriesOptions.Builder().build();
			RelationsOptions relations 		= new RelationsOptions.Builder().build(); // Não funciona com portugues
			ConceptsOptions concepts		= new ConceptsOptions.Builder().build(); // Não funciona com portugues
			EmotionOptions emotion			= new EmotionOptions.Builder().build();  
			SentimentOptions sentiment		= new SentimentOptions.Builder().build();

			Features features = new Features.Builder()
					.categories(categories)
					.relations(relations) // Não funciona com portugues
					.concepts(concepts) // Não funciona com portugues
					.emotion(emotion)
					.sentiment(sentiment)
					.build();

			AnalyzeOptions parameters = new AnalyzeOptions.Builder()
			  .text(text)
			  .features(features)
			  .build();

			AnalysisResults response = naturalLanguageUnderstanding
			  .analyze(parameters)
			  .execute()
			  .getResult();
			
			// Saida no terminal, resposta da requiscao watson
			if (response.getSentiment().getDocument().getLabel() == "negative") {
				System.out.println("Força, tenha fé, tudo de ruin em nossa vida uma dia passa!");
				System.out.println(response);
			}
			
			} catch (NotFoundException e) {
			  // Handle Not Found (404) exception
				System.out.println("Erro 404: Não encontrado!");
			} catch (RequestTooLargeException e) {
			  // Handle Request Too Large (413) exception
				System.out.println("Erro: Sua Requisação demorou muito!");
			} catch (ServiceResponseException e) {
			  // Base class for all exceptions caused by error responses from the service
			  System.out.println("Service returned status code "
			    + e.getStatusCode() + ": " + e.getMessage());
			}

	}
}

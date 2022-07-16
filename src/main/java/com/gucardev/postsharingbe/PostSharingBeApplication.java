package com.gucardev.postsharingbe;

import com.gucardev.postsharingbe.model.*;
import com.gucardev.postsharingbe.repository.PostRepository;
import com.gucardev.postsharingbe.repository.UserRepository;
import com.gucardev.postsharingbe.request.CommentRequest;
import com.gucardev.postsharingbe.request.LikeRequest;
import com.gucardev.postsharingbe.service.NotificationStorageService;
import com.gucardev.postsharingbe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class PostSharingBeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PostSharingBeApplication.class, args);
    }


    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    NotificationStorageService notificationStorageService;

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();
        postRepository.deleteAll();

        User user = userRepository.save(User.builder().username("grkn").name("Gurkan").profileImageUrl("https://avatars.githubusercontent.com/u/25080366?v=4").build());
        User user2 = userRepository.save(User.builder().username("sezai").name("sezai").profileImageUrl("https://pbs.twimg.com/profile_images/1232374207729799170/IqtUTP4s_400x400.jpg").build());
        User user3 = userRepository.save(User.builder().username("serif").name("serif").profileImageUrl("https://i.ytimg.com/vi/0Z996ZDAdPg/maxresdefault.jpg").build());

        Post post1 = postService.create(Post.builder().content("first post, this is test!").user(user).build());
        Post post2 = postService.create(Post.builder().content("Post2 Lorem ipsum dolor sit amet, consectetur adipiscing elit.").user(user2).build());
        Post post3 = postService.create(Post.builder().content("Post3 Lorem ipsum dolor sit amet, consectetur adipiscing elit.").user(user3).build());
        Post post4 = postService.create(Post.builder().content("Post4 Lorem ipsum dolor sit amet, consectetur adipiscing elit.").user(user).build());
        Post post5 = postService.create(Post.builder().content("Post5 Lorem ipsum dolor sit amet, consectetur adipiscing elit.").user(user2).build());
        Post post6 = postService.create(Post.builder().content("Post6 Lorem ipsum dolor sit amet, consectetur adipiscing elit.").user(user2).build());

//        Comment comment = Comment.builder().user(user).content("comment 1").build();
//        Comment comment2 = Comment.builder().user(user3).content("comment 2").build();
//        Comment comment3 = Comment.builder().user(user).content("comment 3").build();

        for (int i = 0; i < 10; i++) {
            postService.addComment(new CommentRequest(post2, Comment.builder().user(user).content("comment " + i).build()));
            postService.addComment(new CommentRequest(post2, Comment.builder().user(user3).content("comment  " + i).build()));
            postService.addComment(new CommentRequest(post1, Comment.builder().user(user).content("comment " + i).build()));
        }


        postService.addLike(new LikeRequest(post1, user2));

        postService.addLike(new LikeRequest(post2, user));
        postService.addLike(new LikeRequest(post2, user3));


        notificationStorageService.clear();

        notificationStorageService.createNotificationStorage(Notification.builder()
                .delivered(false)
                .content("notif 1 comment")
                .notificationType(NotificationType.COMMENT)
                .userFrom(user2)
                .userTo(user).build());


        notificationStorageService.createNotificationStorage(Notification.builder()
                .delivered(false)
                .content("notif 2 comment")
                .notificationType(NotificationType.COMMENT)
                .userFrom(user2)
                .userTo(user).build());

        notificationStorageService.createNotificationStorage(Notification.builder()
                .delivered(false)
                .content("notif 1 for user2 comment")
                .notificationType(NotificationType.COMMENT)
                .userFrom(user)
                .userTo(user2).build());


        notificationStorageService.createNotificationStorage(Notification.builder()
                .delivered(false)
                .content("notif 3 like")
                .notificationType(NotificationType.LIKE)
                .userFrom(user2)
                .userTo(user).build());

    }
}

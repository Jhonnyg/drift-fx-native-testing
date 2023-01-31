
#define GL_SILENCE_DEPRECATION

#include <OpenGL/gl.h>
#include <stdio.h>

#include "com_driftfxnative_application_App.h"

#include "driftcpp.h"

namespace Native
{
    struct Context
    {
        JavaVM*                m_VM;
        jobject                m_JavaRenderer;

        driftfx::Renderer*     m_Renderer;
        driftfx::Swapchain*    m_SwapChain;
        driftfx::TransferType* m_TransferType;

        // Render state
        GLuint                 m_FrameBufferHandle;
    };

    static void Initialize(Context* ctx)
    {
        driftfx::Vec2i size        = ctx->m_Renderer->getSize();
        driftfx::Vec2d logicalSize = ctx->m_Renderer->getLogicalSize();
        driftfx::Vec2d screenScale = ctx->m_Renderer->getScreenScale();
        driftfx::Vec2d userScale   = ctx->m_Renderer->getUserScale();

        printf("Native::Initialize\n");
        printf("Native::driftfx::size        : (%d, %d)\n", size.x,        size.y);
        printf("Native::driftfx::logicalSize : (%f, %f)\n", logicalSize.x, logicalSize.y);
        printf("Native::driftfx::screenScale : (%f, %f)\n", screenScale.x, screenScale.y);
        printf("Native::driftfx::userScale   : (%f, %f)\n", userScale.x,   userScale.y);

        glGenFramebuffers(1, &ctx->m_FrameBufferHandle);
    }

    static void CreateSwapChain(Context* ctx)
    {
        printf("Native::CreateSwapChain\n");
        printf("Native::TransferType : %s\n", ctx->m_TransferType->getId().c_str());

        driftfx::SwapchainConfig cfg = {};
        cfg.imageCount   = 2;
        cfg.size         = ctx->m_Renderer->getSize();
        cfg.transferType = ctx->m_TransferType;
        ctx->m_SwapChain = ctx->m_Renderer->createSwapchain(cfg);
    }

    static void Render(Context* ctx)
    {
        if (ctx->m_SwapChain == 0)
        {
            CreateSwapChain(ctx);
        }

        driftfx::RenderTarget* target = ctx->m_SwapChain->acquire();

        glBindFramebuffer(GL_FRAMEBUFFER, ctx->m_FrameBufferHandle);
        glFramebufferTextureEXT(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, driftfx::GLRenderer::getGLTextureId(target), 0);

        glClearColor(0.506, 0.588, 0.561, 1);
        glClear(GL_COLOR_BUFFER_BIT);

        glFlush();
        glBindFramebuffer(GL_FRAMEBUFFER, 0);

        ctx->m_SwapChain->present(target);
    }
};

JNIEXPORT jlong JNICALL Java_com_driftfxnative_application_App_nativeOnStart(JNIEnv* env, jobject cls, jobject _renderer, jobject _transferType)
{
    printf("App_nativeOnStart\n");
    JavaVM* vm;
    env->GetJavaVM(&vm);

    Native::Context* ctx = new Native::Context();
    ctx->m_VM            = vm;
    ctx->m_JavaRenderer  = env->NewGlobalRef(_renderer);
    ctx->m_Renderer      = driftfx::initializeRenderer(env, ctx->m_JavaRenderer);
    ctx->m_TransferType  = driftfx::getTransferType(env, _transferType);

    Native::Initialize(ctx);

    return (jlong) ctx;
}

JNIEXPORT void JNICALL Java_com_driftfxnative_application_App_nativeInit(JNIEnv* env, jobject cls, jobject _classLoader)
{
    printf("App_nativeInit\n");
    driftfx::initialize(env, _classLoader);
}

JNIEXPORT void JNICALL Java_com_driftfxnative_application_App_nativeOnRender(JNIEnv* env, jobject cls, jlong _nativeCtx)
{
    //printf("App_nativeOnRender\n");
    Native::Context* ctx = (Native::Context*) _nativeCtx;
    Native::Render(ctx);
}

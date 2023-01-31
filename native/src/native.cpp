#include "com_driftfxnative_application_App.h"

#include "stdio.h"

JNIEXPORT void JNICALL Java_com_driftfxnative_application_App_nativeInit(JNIEnv *, jobject)
{
	printf("HELLO from C\n");
}

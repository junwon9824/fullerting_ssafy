
��� Git Actions CI/CD 배포 오류 해결 과정 이 문서는 self-hosted GitHub Actions 러너에서 Kubernetes 클러스터에 배포할 때 발생한 permission denied 오류를 해결한 과정을 기록합니다.

문제 발생: 권한 오류 git push로 CI/CD 워크플로우를 실행했을 때, Deploy to Kubernetes 단계에서 다음과 같은 오류가 발생했습니다.
error: loading config file "/etc/rancher/k3s/k3s.yaml": open /etc/rancher/k3s/k3s.yaml: permission denied 이는 GitHub Actions 워크플로우를 실행하는 사용자(actions-runner)가 K3s 클러스터의 설정 파일인 /etc/rancher/k3s/k3s.yaml을 읽을 권한이 없어서 발생한 문제입니다.

러너 상태 확인 및 재연결 오류 해결을 시도하기 전에, 먼저 GitHub 러너가 제대로 작동하는지 확인했습니다.
Settings > Actions > Runners 페이지에서 러너 상태가 Offline인 것을 확인했습니다.

VirtualBox 서버에 접속하여 ~/actions-runner 디렉터리에서 ./run.sh 스크립트를 실행하여 러너를 재연결했습니다.

러너 상태가 Active로 변경되었고, 워크플로우가 다시 실행되었지만 동일한 권한 오류로 실패했습니다.

kubeconfig 권한 설정 actions-runner 사용자에게 K3s 설정 파일에 대한 직접적인 권한이 없기 때문에, 보안을 위해 Kubeconfig 파일을 GitHub Secrets에 저장하고 워크플로우에서 불러와 사용하기로 결정했습니다.
Kubeconfig 내용 복사:

Bash

sudo cat /etc/rancher/k3s/k3s.yaml 위 명령어로 클러스터 설정 파일의 내용을 복사했습니다.

GitHub Secret 생성:

Settings > Secrets and variables > Actions 페이지에서 New repository secret을 생성했습니다.

이름을 KUBE_CONFIG로 지정하고, 복사한 파일 내용을 붙여넣었습니다.

워크플로우 파일(deploy.yml) 수정: deploy 작업 내 steps에 아래와 같은 단계를 추가했습니다.

YAML

name: Setup Kubeconfig run: | mkdir -p $HOME/.kube echo "${{ secrets.KUBE_CONFIG }}" > $HOME/.kube/config chmod 600 $HOME/.kube/config
최종 해결: KUBECONFIG 환경 변수 설정 KUBE_CONFIG 시크릿을 통해 파일을 생성했음에도 불구하고 kubectl이 여전히 기본 경로(etc/rancher/k3s/k3s.yaml)를 참조하여 오류가 발생했습니다.
이 문제를 최종적으로 해결하기 위해 kubectl 명령어가 실행되기 직전에 KUBECONFIG 환경 변수를 명시적으로 설정했습니다.

deploy.yml 파일의 Deploy to Kubernetes cluster 단계에 아래 코드를 추가했습니다.

YAML

name: Deploy to Kubernetes cluster run: |

명시적으로 Kubeconfig 경로를 설정하여 권한 문제를 해결
export KUBECONFIG=$HOME/.kube/config

이후 kubectl 명령어들...
kubectl apply -f k8s/ kubectl rollout restart deployment/fullerting-backend-deployment 이 과정을 통해 CI/CD 파이프라인이 성공적으로 작동하여 Kubernetes 클러스터에 애플리케이션을 배포할 수 있게 되었습니다.
